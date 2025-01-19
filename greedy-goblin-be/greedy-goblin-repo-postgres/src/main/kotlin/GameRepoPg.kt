package io.greedy.goblin.repo.postgres

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.repo.game.*
import io.greedy.goblin.common.repo.util.safeDbCall
import io.greedy.goblin.common.repo.util.toEither
import io.greedy.goblin.repo.postgres.dao.GameTable
import io.greedy.goblin.repo.postgres.entity.GameEntity
import io.greedy.goblin.repo.postgres.entity.PlayerStateDb
import io.greedy.goblin.repo.postgres.entity.toDomain
import io.greedy.goblin.repo.postgres.util.suspendTransaction
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID
import javax.sql.DataSource

class GameRepoPg(
    private val dataSource: DataSource,
) : IGameRepo {
    override suspend fun getGameData(gameId: GameId): IGameDbResponse =
        safeDbCall {
            transaction {
                GameTable
                    .select { GameTable.gameId eq UUID.fromString(gameId.asString()) }
                    .mapNotNull { row ->
                        val playersJson = row[GameTable.players]
                        val players = Json.decodeFromString<List<GameEntity.Player>>(playersJson)
                        GameData(
                            id = gameId,
                            sceneId = row[GameTable.sceneId]?.let { SceneId(it.toString()) } ?: SceneId.NONE,
                            state = GameState.valueOf(row[GameTable.state]),
                            players = players.map { Player(PlayerId(it.id), it.state.toDomain()) },
                        )
                    }.singleOrNull()
                    .toEither { errorGameNotFound(gameId) }
            }
        }

    override suspend fun updatePlayerStateAfterAction(request: UpdatePLayerStateDbRequest): Either<List<GameError>, Unit> =
        safeDbCall {
            suspendTransaction {
                GameTable
                    .select {
                        GameTable.gameId eq UUID.fromString(request.gameId.asString())
                    }.singleOrNull()
                    .toEither { errorGameNotFound(request.gameId) }
                    .map { gameRow ->
                        val playersJson = gameRow[GameTable.players]
                        val players = Json.decodeFromString<List<GameEntity.Player>>(playersJson)

                        players.filter { it.id != request.playerId.asString() } +
                            GameEntity.Player(
                                id = request.playerId.asString(),
                                state = PlayerStateDb.ACTION_MADE,
                            )
                    }.flatMap { playersList ->
                        val updateQuery =
                            """
                            UPDATE games
                            SET players = ?::jsonb
                            WHERE game_id = ?::uuid
                            """.trimIndent()
                        val prepStatement = connection.prepareStatement(updateQuery, false)
                        prepStatement[1] = Json.encodeToString(playersList)
                        prepStatement[2] = request.gameId.asString()
                        prepStatement.executeUpdate()
                        Unit.right()
                    }
            }
        }

    override suspend fun refreshState(gameId: GameId): Either<List<GameError>, Unit> =
        safeDbCall {
            suspendTransaction {
                GameTable
                    .select {
                        GameTable.gameId eq UUID.fromString(gameId.asString())
                    }.singleOrNull()
                    .toEither { errorGameNotFound(gameId) }
                    .map { gameRow ->
                        val playersJson = gameRow[GameTable.players]
                        val players = Json.decodeFromString<List<GameEntity.Player>>(playersJson)

                        players.map { player ->
                            player.copy(state = PlayerStateDb.WAITING_FOR_ACTION)
                        }
                    }.flatMap { playersList ->
                        val updateQuery =
                            """
                            UPDATE games
                            SET players = ?::jsonb
                            WHERE game_id = ?::uuid
                            """.trimIndent()
                        val prepStatement = connection.prepareStatement(updateQuery, false)
                        prepStatement[1] = Json.encodeToString(playersList)
                        prepStatement[2] = gameId.asString()
                        prepStatement.executeUpdate()
                        Unit.right()
                    }
            }
        }

    override suspend fun setScene(request: SetSceneDbRequest): Either<List<GameError>, Unit> =
        safeDbCall {
            suspendTransaction {
                GameTable.update({ GameTable.gameId eq UUID.fromString(request.gameId.asString()) }) {
                    it[sceneId] = UUID.fromString(request.sceneId.asString())
                }
                Unit.right()
            }
        }

    override suspend fun createGame(): Either<List<GameError>, GameId> =
        safeDbCall {
            dataSource.connection.use { connection ->
                val query = "INSERT INTO games (game_id, scene_id, state, players) VALUES (?, ?, ?, ?::jsonb)"
                val gameId = UUID.randomUUID()
                val sceneId: UUID? = null
                val state = GameState.ACTIVE.name
                val playersJson = """[]""" // Пример JSON
                connection.prepareStatement(query).use { preparedStatement ->
                    preparedStatement.setObject(1, gameId)
                    preparedStatement.setObject(2, sceneId)
                    preparedStatement.setString(3, state)
                    preparedStatement.setString(4, playersJson)

                    preparedStatement.executeUpdate()
                    connection.commit()
                }
                GameId(gameId.toString()).right()
            }
        }
}
