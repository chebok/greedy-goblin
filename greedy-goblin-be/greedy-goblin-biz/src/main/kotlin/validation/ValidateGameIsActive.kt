package io.greedy.goblin.biz.validation

import io.greedy.goblin.biz.validation.validators.activeGameValidate
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.errorValidation
import io.greedy.goblin.common.helpers.fail
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.activeGameValidate(title: String) =
    worker {
        this.title = title
        this.description =
            """
            Проверяем, что игра активна.
            """.trimIndent()

        on {
            val validationResult = activeGameValidate()
            validationResult.errors.isNotEmpty()
        }
        handle {
            fail(
                errorValidation(
                    field = "gameId",
                    violationCode = "notActive",
                    description = "Game not active now",
                ),
            )
        }
    }
