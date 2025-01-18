package io.greedy.goblin.biz

import io.greedy.goblin.biz.general.*
import io.greedy.goblin.biz.logic.createGameProcessing
import io.greedy.goblin.biz.logic.fillGameContext
import io.greedy.goblin.biz.logic.playerActionProcessing
import io.greedy.goblin.biz.logic.refreshActiveScene
import io.greedy.goblin.biz.stubs.*
import io.greedy.goblin.biz.validation.activeGameValidate
import io.greedy.goblin.biz.validation.validateActionAvailable
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

            operation("Запрос на создание игры", GameCommand.START) {
                stubs("Обработка стабов") {
                    gameStartSuccess("Имитация успешной обработки", corSettings)
                }
                createGameProcessing("Создание игры", corSettings)
                prepareResult("Завершение обработки")
            }

            operation("Запрос сцены", GameCommand.GET_SCENE) {
                stubs("Обработка стабов") {
                    getSceneSuccess("Имитация успешной обработки", corSettings)
                    internalServerError("Имитация ошибки сервера")
                }
                validation {
                    activeGameValidate("Проверка на доступность игры")
                }
                fillGameContext("Заполнение игрового контекста", corSettings)
                refreshActiveScene("Запрос расчета новой сцены", corSettings)
                prepareResult("Завершение обработки")
            }

            operation("Действие в игре", GameCommand.ACTION) {
                stubs("Обработка стабов") {
                    gameActionSuccess("Имитация успешной обработки", corSettings)
                    validationBadAction("Имитация ошибки валидации действия")
                }
                validation {
                    validateActionAvailable("Проверка на доступность действия")
                }
                fillGameContext("Заполнение игрового контекста", corSettings)
                playerActionProcessing("Обработка действия игрока", corSettings)
                prepareResult("Завершение обработки")
            }
        }.build()
}
