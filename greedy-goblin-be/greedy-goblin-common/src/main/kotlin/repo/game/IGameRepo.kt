package io.greedy.goblin.common.repo.game

import arrow.core.Either
import arrow.core.left
import io.greedy.goblin.common.models.GameError
import io.greedy.goblin.common.models.GameId
import io.greedy.goblin.common.repo.util.errorNotImplemented

interface IGameRepo {
    suspend fun getGameData(gameId: GameId): IGameDbResponse

    suspend fun updatePlayerStateAfterAction(request: UpdatePLayerStateDbRequest): Either<List<GameError>, Unit>

    suspend fun refreshState(gameId: GameId): Either<List<GameError>, Unit>

    suspend fun setScene(request: SetSceneDbRequest): Either<List<GameError>, Unit>

    suspend fun createGame(): Either<List<GameError>, GameId>

    companion object {
        val NONE =
            object : IGameRepo {
                override suspend fun getGameData(gameId: GameId): IGameDbResponse = errorNotImplemented("getGameData").left()

                override suspend fun updatePlayerStateAfterAction(request: UpdatePLayerStateDbRequest): Either<List<GameError>, Unit> =
                    errorNotImplemented("updatePlayerState").left()

                override suspend fun refreshState(gameId: GameId): Either<List<GameError>, Unit> =
                    errorNotImplemented("refreshState").left()

                override suspend fun setScene(request: SetSceneDbRequest): Either<List<GameError>, Unit> =
                    errorNotImplemented("setScene").left()

                override suspend fun createGame(): Either<List<GameError>, GameId> = errorNotImplemented("createGame").left()
            }
    }
}
