package io.greedy.goblin.common.repo.game

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class GameRepoInitialized(
    private val repo: IGameRepoInitializable,
    initObjects: Collection<GameData> = emptyList(),
) : IGameRepoInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<GameData> = seed(initObjects).toList()
}
