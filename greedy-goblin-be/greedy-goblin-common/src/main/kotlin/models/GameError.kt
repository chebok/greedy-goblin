package models

data class GameError(
    val code: String = "",
    val message: String = "",
    val group: String = "",
    val exception: Throwable? = null,
)
