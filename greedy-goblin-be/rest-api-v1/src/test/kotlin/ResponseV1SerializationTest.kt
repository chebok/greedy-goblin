package io.greedy.goblin.api.v1

import io.greedy.goblin.api.v1.models.*
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response =
        GameSceneResponse(
            success = true,
            scene =
                GameSceneView(
                    sceneId = "scene123",
                    gameId = "game456",
                    image = "https://example.com/images/scene.jpg",
                    sceneTitle = "You are in a dark forest, with mushrooms scattered around.",
                    playerActions =
                        listOf(
                            GameSceneViewPlayerActionsInner("1", "Move forward"),
                            GameSceneViewPlayerActionsInner("2", "Pick mushrooms"),
                            GameSceneViewPlayerActionsInner("3", "Look around"),
                        ),
                ),
        )

    @Test
    fun serialize() {
        val json = apiV1Serializer.encodeToString(response)

        assertContains(json, Regex("\"success\":\\s*true"))
        assertContains(
            json,
            Regex(
                "\\{\"id\":\\s*\"1\",\\s*\"title\":\\s*\"Move forward\"}",
            ),
        )
    }

    @Test
    fun deserialize() {
        val json = apiV1Serializer.encodeToString(response)
        val obj = apiV1Serializer.decodeFromString<GameSceneResponse>(json)

        assertEquals(response, obj)
    }
}
