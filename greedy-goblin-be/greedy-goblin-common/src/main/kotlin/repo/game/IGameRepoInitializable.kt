package io.greedy.goblin.common.repo.game

interface IGameRepoInitializable : IGameRepo {
    fun seed(ads: Collection<GameData>): Collection<GameData>
}
