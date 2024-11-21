package models

@JvmInline
value class ActionId(
    private val id: String,
) {
    fun asString() = id

    companion object {
        val NONE = ActionId("")
    }
}
