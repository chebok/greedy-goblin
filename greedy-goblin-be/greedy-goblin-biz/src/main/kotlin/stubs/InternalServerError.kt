package io.greedy.goblin.biz.stubs

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.fail
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.models.GameError
import io.greedy.goblin.common.stubs.AppStubs
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.internalServerError(title: String) =
    worker {
        this.title = title
        this.description =
            """
            Кейс ошибки базы данных
            """.trimIndent()
        on { stubCase == AppStubs.SERVER_ERROR && commandState == CommandState.RUN }
        handle {
            fail(
                GameError(
                    group = "internal",
                    code = "internal-db",
                    message = "Internal error",
                ),
            )
        }
    }
