package io.greedy.goblin.api.v1

import kotlinx.serialization.json.Json

val apiV1Serializer =
    Json {
        ignoreUnknownKeys = true // Игнорирует неизвестные поля в JSON
        encodeDefaults = true // Включает значения по умолчанию
        isLenient = true // Позволяет более гибкий парсинг JSON
    }
