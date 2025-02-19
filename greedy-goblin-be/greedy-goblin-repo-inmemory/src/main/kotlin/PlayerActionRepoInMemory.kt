package io.greedy.goblin.repo.inmemory

import arrow.core.right
import io.github.reactivecircus.cache4k.Cache
import io.greedy.goblin.common.repo.player.IPlayerActionDbResponse
import io.greedy.goblin.common.repo.player.IPlayerActionRepo
import io.greedy.goblin.common.repo.player.PlayerActionDbRequest
import io.greedy.goblin.common.repo.util.safeDbCall
import io.greedy.goblin.repo.inmemory.entity.PlayerActionEntity
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class PlayerActionRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { UUID.randomUUID().toString() },
) : IPlayerActionRepo {
    private val cache =
        Cache
            .Builder<String, PlayerActionEntity>()
            .expireAfterWrite(ttl)
            .build()

    override suspend fun save(request: PlayerActionDbRequest): IPlayerActionDbResponse =
        safeDbCall {
            val idKey = randomUuid()
            val (gameId, playerId, actionId, sceneId) = request
            val entity =
                PlayerActionEntity(
                    gameId = gameId.asString(),
                    playerId = playerId.asString(),
                    actionId = actionId.asString(),
                    sceneId = sceneId.asString(),
                )
            cache.put(idKey, entity)
            Unit.right()
        }
}
