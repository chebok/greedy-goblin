package io.greedy.goblin.biz.validation

import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.models.GameCommand

abstract class BaseBizValidationTest {
    protected abstract val command: GameCommand
    private val settings by lazy { CorSettings() }
    protected val processor by lazy { GameLogicProcessor(settings) }
}
