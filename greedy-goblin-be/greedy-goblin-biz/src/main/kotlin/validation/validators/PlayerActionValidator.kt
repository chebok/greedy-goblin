package io.greedy.goblin.biz.validation.validators

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.ActionId
import io.konform.validation.Validation
import io.konform.validation.ValidationBuilder
import io.konform.validation.ValidationResult
import kotlinx.coroutines.delay

suspend fun GameContext.playerActionValidate(): ValidationResult<GameContext> {
    val allowedActions = getAllowedActions()
    val validation =
        Validation<GameContext> {
            GameContext::playerAction {
                isActionAvailable(allowedActions)
            }
        }
    return validation.validate(this)
}

private fun ValidationBuilder<ActionId>.isActionAvailable(allowedActions: List<ActionId>) {
    constrain(
        hint = "Action is not available",
    ) {
        it in allowedActions
    }
}

private suspend fun getAllowedActions(): List<ActionId> {
    // In the future, add database or service call to verify action availability.
    delay(100)
    return listOf(ActionId("action-001"), ActionId("action-002"))
}
