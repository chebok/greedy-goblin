package io.greedy.goblin.biz.validation.validators

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.GameId
import io.konform.validation.Validation
import io.konform.validation.ValidationBuilder
import io.konform.validation.ValidationResult
import kotlinx.coroutines.delay

suspend fun GameContext.activeGameValidate(): ValidationResult<GameContext> {
    val activeGames = getActiveGames()
    val validation =
        Validation<GameContext> {
            GameContext::gameId {
                isGameActive(activeGames)
            }
        }
    return validation.validate(this)
}

private fun ValidationBuilder<GameId>.isGameActive(activeGames: List<GameId>) {
    constrain(
        hint = "Game not active",
    ) {
//        it in activeGames
        true
    }
}

private suspend fun getActiveGames(): List<GameId> {
    // In the future, add database or service call to verify games.
    delay(100)
    return listOf(GameId("game-started-123"))
}
