package io.greedy.goblin.biz.general

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.chain

fun ICorChainDsl<GameContext>.validation(block: ICorChainDsl<GameContext>.() -> Unit) =
    chain {
        block()
        title = "Валидация"

        on { commandState == CommandState.RUN }
    }
