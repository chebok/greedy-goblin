package io.greedy.goblin.biz.general

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.models.GameCommand
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.chain

fun ICorChainDsl<GameContext>.operation(
    title: String,
    command: GameCommand,
    block: ICorChainDsl<GameContext>.() -> Unit,
) = chain {
    block()
    this.title = title
    on { this.command == command && commandState == CommandState.RUN }
}
