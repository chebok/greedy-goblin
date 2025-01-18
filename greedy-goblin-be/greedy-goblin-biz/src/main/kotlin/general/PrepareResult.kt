package io.greedy.goblin.biz.general

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.AppWorkMode
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.prepareResult(title: String) =
    worker {
        this.title = title
        on { workMode != AppWorkMode.STUB }
        handle {
            commandState =
                when (val st = commandState) {
                    CommandState.RUN -> CommandState.FINISH
                    else -> st
                }
        }
    }
