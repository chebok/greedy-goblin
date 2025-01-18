package io.greedy.goblin.common.repo.player

import arrow.core.left
import io.greedy.goblin.common.repo.util.errorNotImplemented

interface IPlayerActionRepo {
    suspend fun save(request: PlayerActionDbRequest): IPlayerActionDbResponse

    companion object {
        val NONE =
            object : IPlayerActionRepo {
                override suspend fun save(request: PlayerActionDbRequest): IPlayerActionDbResponse = errorNotImplemented("save").left()
            }
    }
}
