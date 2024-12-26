package io.greedy.goblin.common

import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.stubs.AppStubs
import kotlinx.datetime.Instant

data class GameContext(
    // Команда и состояние
    var command: GameCommand = GameCommand.NONE, // Текущая команда, например, начало игры или действие
    var commandState: CommandState = CommandState.NONE, // Состояние выполнения команды
    // Игра и сцена
    var playerId: PlayerId = PlayerId.NONE, // Уникальный идентификатор игрока
    var gameId: GameId = GameId.NONE, // Уникальный идентификатор игры
    var gameState: GameState = GameState.NONE, // Текущее состояние игрового процесса
    var gameScene: GameScene = GameScene(), // Текущая игровая сцена
    var playerAction: ActionId = ActionId.NONE, // Идентификатор действия, выбранного игроком
    // Диагностика и ошибки
    val errors: MutableList<GameError> = mutableListOf(), // Список ошибок, возникших в процессе обработки
    var requestId: RequestId = RequestId.NONE, // Уникальный идентификатор запроса для трейсинга
    var startTime: Instant = Instant.NONE, // Время начала обработки запроса для измерения производительности
    // Режим работы и стабы
    var corSettings: CorSettings = CorSettings(),
    var workMode: AppWorkMode = AppWorkMode.PROD, // Режим работы приложения (продакшн, тестовый, отладочный)
    var stubCase: AppStubs = AppStubs.NONE, // Текущий стаб, используемый для отладки
)
