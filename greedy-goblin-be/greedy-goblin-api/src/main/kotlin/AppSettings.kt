package io.greedy.goblin.api

import io.greedy.goblin.api.common.IAppSettings
import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.CorSettings

data class AppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: CorSettings = CorSettings(),
    override val processor: GameLogicProcessor = GameLogicProcessor(corSettings),
) : IAppSettings
