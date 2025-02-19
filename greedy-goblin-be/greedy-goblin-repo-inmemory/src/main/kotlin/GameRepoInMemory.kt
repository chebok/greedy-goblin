package io.greedy.goblin.repo.inmemory

import arrow.core.Either
import arrow.core.right
import io.github.reactivecircus.cache4k.Cache
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.repo.game.*
import io.greedy.goblin.common.repo.util.safeDbCall
import io.greedy.goblin.common.repo.util.toEither
import io.greedy.goblin.repo.inmemory.entity.GameEntity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class GameRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { UUID.randomUUID().toString() },
) : IGameRepo {
    private val mutex: Mutex = Mutex()
    private val cache =
        Cache
            .Builder<String, GameEntity>()
            .expireAfterWrite(ttl)
            .build()

    override suspend fun getGameData(gameId: GameId): IGameDbResponse =
        safeDbCall {
            cache
                .get(gameId.asString())
                .toEither { errorGameNotFound(gameId) }
                .map { game ->
                    GameData(
                        id = gameId,
                        sceneId = game.sceneId?.let { SceneId(it) } ?: SceneId.NONE,
                        state = game.state,
                        players = game.players.map { Player(PlayerId(it.id), it.state) },
                    )
                }
        }

    override suspend fun updatePlayerStateAfterAction(request: UpdatePLayerStateDbRequest): Either<List<GameError>, Unit> =
        safeDbCall {
            val (gameId, playerId) = request
            val idKey = gameId.asString()

            cache
                .get(idKey)
                .toEither { errorGameNotFound(gameId) }
                .map { game ->
                    val updatedGame =
                        game.copy(
                            players =
                                game.players.map { player ->
                                    if (player.id == playerId.asString()) {
                                        player.copy(state = PlayerState.ACTION_MADE)
                                    } else {
                                        player
                                    }
                                },
                        )
                    mutex.withLock {
                        cache.put(idKey, updatedGame)
                    }
                }
        }

    override suspend fun refreshState(gameId: GameId): Either<List<GameError>, Unit> =
        safeDbCall {
            val idKey = gameId.asString()

            cache
                .get(idKey)
                .toEither { errorGameNotFound(gameId) }
                .map { game ->
                    val updatedGame =
                        game.copy(
                            players =
                                game.players.map {
                                    it.copy(state = PlayerState.WAITING_FOR_ACTION)
                                },
                        )
                    mutex.withLock {
                        cache.put(idKey, updatedGame)
                    }
                }
        }

    override suspend fun setScene(request: SetSceneDbRequest): Either<List<GameError>, Unit> =
        safeDbCall {
            val (gameId, sceneId) = request
            val idKey = gameId.asString()

            cache.get(idKey).toEither { errorGameNotFound(gameId) }.map { game ->
                val updatedGame =
                    game.copy(
                        sceneId = sceneId.asString(),
                    )

                mutex.withLock {
                    cache.put(idKey, updatedGame)
                }
            }
        }

    override suspend fun createGame(): Either<List<GameError>, GameId> =
        safeDbCall {
            val idKey = randomUuid()
            val entity =
                GameEntity(
                    sceneId = null,
                    state = GameState.ACTIVE,
                    players = emptyList(),
                )
            cache.put(idKey, entity)
            GameId(idKey).right()
        }
}
