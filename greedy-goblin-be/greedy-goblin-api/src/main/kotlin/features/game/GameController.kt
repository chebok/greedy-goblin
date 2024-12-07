package io.greedy.goblin.api.features.game

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.api.common.controllerHelper
import io.greedy.goblin.api.v1.mappers.fromTransport
import io.greedy.goblin.api.v1.mappers.toTransport
import io.greedy.goblin.api.v1.models.GameStartRequest
import io.greedy.goblin.api.v1.models.GameStartResponse
import kotlin.reflect.KClass

class GameController(
    private val appSettings: AppSettings,
) {
    suspend fun startGame(request: GameStartRequest): GameStartResponse =
        process(
            appSettings = appSettings,
            request = request,
            clazz = this::class,
            logId = "start-game",
        )

    companion object {
        suspend inline fun <reified Q : Any, reified R> process(
            appSettings: AppSettings,
            request: Q,
            clazz: KClass<*>,
            logId: String,
        ): R =
            appSettings.controllerHelper(
                {
                    fromTransport(request)
                },
                { toTransport() as R },
                clazz,
                logId,
            )
    }
}
