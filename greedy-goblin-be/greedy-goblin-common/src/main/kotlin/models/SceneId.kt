package io.greedy.goblin.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class SceneId(
    private val id: String,
) {
    fun asString() = id

    companion object {
        val NONE = SceneId("")
    }
}
