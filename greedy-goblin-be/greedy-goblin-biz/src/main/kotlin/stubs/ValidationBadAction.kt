package io.greedy.goblin.biz.stubs

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.fail
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.models.GameError
import io.greedy.goblin.common.stubs.AppStubs
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.validationBadAction(title: String) =
    worker {
        this.title = title
        this.description =
            """
            Кейс ошибки валидации для заголовка
            """.trimIndent()

        on { stubCase == AppStubs.BAD_REQUEST && commandState == CommandState.RUN }
        handle {
            fail(
                GameError(
                    group = "validation",
                    code = "validation-action",
                    message = "Wrong action",
                ),
            )
        }
    }
