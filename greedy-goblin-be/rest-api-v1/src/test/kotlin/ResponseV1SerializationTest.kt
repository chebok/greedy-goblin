package io.goblingamble.api.v1

import io.goblingamble.api.v1.models.*
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response =
        SceneResponse(
            success = true,
            scene =
                Scene(
                    sceneId = "scene123",
                    gameId = "game456",
                    image = "https://example.com/images/scene.jpg",
                    description = "You are in a dark forest, with mushrooms scattered around.",
                    actions = listOf("Move forward", "Pick mushrooms", "Look around"),
                ),
        )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(response)

        assertContains(json, Regex("\"success\":\\s*true"))
        assertContains(json, Regex("\"actions\":\\s*\\[\"Move forward\",\\s*\"Pick mushrooms\",\\s*\"Look around\"\\]"))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(response)
        val obj = apiV1Mapper.decodeFromString<SceneResponse>(json)

        assertEquals(response, obj)
    }
}
