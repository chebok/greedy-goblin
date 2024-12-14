package io.greedy.goblin.api.common

import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.CorSettings

interface IAppSettings {
    val processor: GameLogicProcessor
    val corSettings: CorSettings
}
