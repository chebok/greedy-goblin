package io.greedy.goblin.biz.general

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.initStatus(title: String) =
    worker {
        this.title = title
        this.description =
            """
            Этот обработчик устанавливает стартовый статус обработки. Запускается только в случае не заданного статуса.
            """.trimIndent()
        on { commandState == CommandState.NONE }
        handle { commandState = CommandState.RUN }
    }
