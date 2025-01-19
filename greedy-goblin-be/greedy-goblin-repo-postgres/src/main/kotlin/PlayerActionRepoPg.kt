package io.greedy.goblin.repo.postgres

import arrow.core.right
import io.greedy.goblin.common.repo.player.IPlayerActionDbResponse
import io.greedy.goblin.common.repo.player.IPlayerActionRepo
import io.greedy.goblin.common.repo.player.PlayerActionDbRequest
import io.greedy.goblin.common.repo.util.safeDbCall
import io.greedy.goblin.repo.postgres.dao.PlayerActionDAO
import io.greedy.goblin.repo.postgres.util.suspendTransaction
import java.util.*

class PlayerActionRepoPg : IPlayerActionRepo {
    override suspend fun save(request: PlayerActionDbRequest): IPlayerActionDbResponse =
        safeDbCall {
            suspendTransaction {
                PlayerActionDAO.new {
                    gameId = UUID.fromString(request.gameId.asString())
                    playerId = UUID.fromString(request.playerId.asString())
                    actionId = request.actionId.asString()
                    sceneId = UUID.fromString(request.sceneId.asString())
                }
                Unit.right()
            }
        }
}
