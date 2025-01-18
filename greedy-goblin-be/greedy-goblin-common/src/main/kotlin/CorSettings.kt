package io.greedy.goblin.common

import io.greedy.goblin.common.repo.game.IGameRepo
import io.greedy.goblin.common.repo.player.IPlayerActionRepo
import io.greedy.goblin.common.repo.scene.ISceneRepo
import io.greedy.goblin.libs.logging.common.MpLoggerProvider

data class CorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val playerActionRepo: IPlayerActionRepo = IPlayerActionRepo.NONE,
    val gameRepo: IGameRepo = IGameRepo.NONE,
    val sceneRepo: ISceneRepo = ISceneRepo.NONE,
) {
    companion object {
        val NONE = CorSettings()
    }
}
