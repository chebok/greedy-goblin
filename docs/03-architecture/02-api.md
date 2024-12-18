# API

## Описание игровой сцены

Игровая сцена предоставляет игроку:
1. **sceneId**: Уникальный идентификатор сцены.
2. **gameId**: Идентификатор текущей игровой сессии, позволяющий различать параллельные игры.
3. **image**: Визуальное представление текущей ситуации.
4. **description**: Краткий текст, описывающий происходящее.
5. **actions**: Доступные игроку действия.

## Функции (эндпоинты)

### HTTP Эндпоинты для single-player (REST API)

1. **Начало игры** (`POST /api/game/start`): Инициализация новой игровой сессии. Возвращает `gameId`.
2. **Получение активной сцены** (`GET /api/game/scene`): Возвращает текущую сцену с `sceneId`, описанием, картинкой и вариантами доступных действий.
3. **Совершение действия** (`POST /api/game/action`): Принимает выбранное игроком действие и сохраняет его; мобильное приложение затем самостоятельно обновляет сцену через `/api/game/scene`.
4. **Завершение игры** (`POST /api/game/end`): Завершает текущую игровую сессию.

### WebSocket Протокол для multi-player (WS API)

1. **Установление соединения** (`/ws/connect`): Клиент устанавливает постоянное соединение, через которое обменивается всеми игровыми сообщениями.

2. **Сообщения WebSocket**:
    - **Начало игры**: `{ "type": "startGame" }` — Инициализирует новую сессию, сервер отвечает с `gameId` и первой сценой.
    - **Получение активной сцены**: `{ "type": "getScene" }` — Запрос на текущую сцену, сервер возвращает сцену с описанием и действиями.
    - **Совершение действия**: `{ "type": "performAction", "actionId": "id действия" }` — Клиент отправляет действие; сервер сохраняет и, в мультиплеере, ждет всех участников перед обновлением.
    - **Обновление сцены**: `{ "type": "updateScene", "scene": { ... } }` — Сервер отправляет новую сцену всем игрокам после получения всех действий.
    - **Завершение игры**: `{ "type": "endGame" }` — Завершает сессию, закрывая соединение.

### Коммуникация

- **REST API**: Используется для single-player режима, обеспечивает запуск игры, загрузку сцены, выполнение действий и завершение игры через HTTP.
- **WebSocket**: Используется для multi-player режима, где все взаимодействия, включая старт игры, совершение действий и обновление сцены, происходят через постоянное соединение.
