package io.greedy.goblin.biz.stubs

import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.stubs.AppStubs
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker
import io.greedy.goblin.libs.logging.common.LogLevel

fun ICorChainDsl<GameContext>.getSceneSuccess(
    title: String,
    corSettings: CorSettings,
) = worker {
    this.title = title
    this.description =
        """
        Кейс успешного получения сцены
        """.trimIndent()
    on { stubCase == AppStubs.SUCCESS && commandState == CommandState.RUN }
    val logger = corSettings.loggerProvider.logger("getSceneSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            gameScene =
                GameScene(
                    sceneId = SceneId("scene-001"),
                    description = "You see a path splitting in two.",
                    image = "",
                    actions =
                        listOf(
                            Action(ActionId("action-001"), "Take the left path"),
                            Action(ActionId("action-002"), "Take the right path"),
                        ),
                )
            commandState = CommandState.FINISH
        }
    }
}
