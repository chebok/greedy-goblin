package io.greedy.goblin.biz.general

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.AppWorkMode
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.chain

fun ICorChainDsl<GameContext>.stubs(
    title: String,
    block: ICorChainDsl<GameContext>.() -> Unit,
) = chain {
    block()
    this.title = title
    on { workMode == AppWorkMode.STUB && commandState == CommandState.RUN }
}
