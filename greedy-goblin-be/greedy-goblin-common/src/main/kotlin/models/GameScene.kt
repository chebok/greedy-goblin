package models

data class GameScene(
    var sceneId: SceneId = SceneId.NONE, // Уникальный идентификатор сцены
    var gameId: GameId = GameId.NONE, // Идентификатор игры, к которой относится сцена
    var description: String = "", // Описание текущей ситуации
    var image: String = "", // URL или URI к изображению сцены
    var actions: List<Action> = emptyList(), // Список доступных действий для игрока
)
