package io.greedy.goblin.repo.inmemory

import arrow.core.Either
import arrow.core.right
import io.github.reactivecircus.cache4k.Cache
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.repo.scene.CreateSceneDbRequest
import io.greedy.goblin.common.repo.scene.ISceneRepo
import io.greedy.goblin.common.repo.scene.errorSceneNotFound
import io.greedy.goblin.common.repo.util.safeDbCall
import io.greedy.goblin.common.repo.util.toEither
import io.greedy.goblin.repo.inmemory.entity.SceneEntity
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class SceneRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { UUID.randomUUID().toString() },
) : ISceneRepo {
    private val cache =
        Cache
            .Builder<String, SceneEntity>()
            .expireAfterWrite(ttl)
            .build()

    override suspend fun save(request: CreateSceneDbRequest): Either<List<GameError>, SceneId> =
        safeDbCall {
            val idKey = randomUuid()
            val (description, image, actions) = request
            val entity =
                SceneEntity(
                    description = description,
                    image = image,
                    actions = actions.map { it -> SceneEntity.Action(id = it.id.asString(), title = it.title) },
                )
            cache.put(idKey, entity)
            SceneId(idKey).right()
        }

    override suspend fun getSceneData(sceneId: SceneId): Either<List<GameError>, GameScene> =
        safeDbCall {
            cache
                .get(sceneId.asString())
                .toEither { errorSceneNotFound(sceneId) }
                .map { scene ->
                    GameScene(
                        sceneId = sceneId,
                        description = scene.description,
                        image = scene.image,
                        actions = scene.actions.map { Action(ActionId(it.id), it.title) },
                    )
                }
        }
}
