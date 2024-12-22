package io.greedy.goblin.biz.validation

import io.greedy.goblin.biz.validation.validators.playerActionValidate
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.errorValidation
import io.greedy.goblin.common.helpers.fail
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.validateActionAvailable(title: String) =
    worker {
        this.title = title
        this.description =
            """
            Проверяем, что такой действие доступно игроку.
            """.trimIndent()

        on {
            val validationResult = playerActionValidate()
            validationResult.errors.isNotEmpty()
        }
        handle {
            fail(
                errorValidation(
                    field = "playerAction",
                    violationCode = "notAvailable",
                    description = "Action is not available now",
                ),
            )
        }
    }
