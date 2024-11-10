package io.goblingamble.api.v1

import io.goblingamble.api.v1.models.*
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request =
        GameStartRequest(
            mode = GameStartRequest.Mode.SINGLE,
        )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(request)

        assertContains(json, Regex("\"mode\":\\s*\"single\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(request)
        val obj = apiV1Mapper.decodeFromString<GameStartRequest>(json)

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString =
            """
            {"mode": null}
            """.trimIndent()
        val obj = apiV1Mapper.decodeFromString<GameStartRequest>(jsonString)

        assertEquals(null, obj.mode)
    }
}
