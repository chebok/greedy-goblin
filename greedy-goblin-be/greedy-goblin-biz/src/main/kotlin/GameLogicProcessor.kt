package io.greedy.goblin.biz

import io.greedy.goblin.biz.general.initStatus
import io.greedy.goblin.biz.general.operation
import io.greedy.goblin.biz.general.stubs
import io.greedy.goblin.biz.stubs.*
import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.GameCommand
import io.greedy.goblin.libs.cor.rootChain

@Suppress("unused", "RedundantSuspendModifier")
class GameLogicProcessor(
    val corSettings: CorSettings = CorSettings.NONE,
) {
    suspend fun exec(ctx: GameContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain =
        rootChain<GameContext> {
            initStatus("Инициализация статуса")

            operation("Запрос на старт игры", GameCommand.START) {
                stubs("Обработка стабов") {
                    gameStartSuccess("Имитация успешной обработки", corSettings)
                }
            }

            operation("Запрос сцены", GameCommand.GET_SCENE) {
                stubs("Обработка стабов") {
                    getSceneSuccess("Имитация успешной обработки", corSettings)
                    internalServerError("Имитация ошибки сервера")
                }
            }

            operation("Действие в игре", GameCommand.ACTION) {
                stubs("Обработка стабов") {
                    gameActionSuccess("Имитация успешной обработки", corSettings)
                    validationBadAction("Имитация ошибки валидации действия")
                }
            }
        }.build()
}
