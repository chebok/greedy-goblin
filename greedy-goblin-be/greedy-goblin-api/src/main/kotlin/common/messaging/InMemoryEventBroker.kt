package io.greedy.goblin.api.common.messaging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

object InMemoryEventBroker {
    private val sceneUpdateTopic = MutableSharedFlow<SceneUpdate>(extraBufferCapacity = 100)

    suspend fun emitSceneUpdate(update: SceneUpdate) {
        sceneUpdateTopic.emit(update)
    }

    fun subscribeSceneUpdate(): Flow<SceneUpdate> = sceneUpdateTopic
}
