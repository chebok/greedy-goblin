package io.greedy.goblin.biz

import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext

@Suppress("unused", "RedundantSuspendModifier")
class GameLogicProcessor(
    val corSettings: CorSettings,
) {
    suspend fun exec(ctx: GameContext) {
        GameStub.processContext(ctx)
    }
}
