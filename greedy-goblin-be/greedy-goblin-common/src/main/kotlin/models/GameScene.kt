package io.greedy.goblin.common.models

data class GameScene(
    var sceneId: SceneId = SceneId.NONE, // Уникальный идентификатор сцены
    var description: String = "", // Описание текущей ситуации
    var image: String = "", // URL или URI к изображению сцены
    var actions: List<Action> = emptyList(), // Список доступных действий для игрока
)
