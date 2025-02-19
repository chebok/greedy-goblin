package io.greedy.goblin.common.repo.scene

import arrow.core.Either
import arrow.core.left
import io.greedy.goblin.common.models.GameError
import io.greedy.goblin.common.models.GameScene
import io.greedy.goblin.common.models.SceneId
import io.greedy.goblin.common.repo.util.errorNotImplemented

interface ISceneRepo {
    suspend fun save(request: CreateSceneDbRequest): Either<List<GameError>, SceneId>

    suspend fun getSceneData(sceneId: SceneId): Either<List<GameError>, GameScene>

    companion object {
        val NONE =
            object : ISceneRepo {
                override suspend fun save(request: CreateSceneDbRequest): Either<List<GameError>, SceneId> =
                    errorNotImplemented("save").left()

                override suspend fun getSceneData(sceneId: SceneId): Either<List<GameError>, GameScene> =
                    errorNotImplemented("getSceneData").left()
            }
    }
}
