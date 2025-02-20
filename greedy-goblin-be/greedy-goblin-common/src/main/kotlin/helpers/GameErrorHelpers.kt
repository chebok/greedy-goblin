package io.greedy.goblin.common.helpers

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.models.GameError
import io.greedy.goblin.libs.logging.common.LogLevel

fun Throwable.asGameError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = GameError(
    code = code,
    group = group,
    message = message,
    exception = this,
)

fun GameContext.addError(error: GameError) = errors.add(error)

fun GameContext.addErrors(error: Collection<GameError>) = errors.addAll(error)

fun GameContext.fail(error: GameError) {
    addError(error)
    commandState = CommandState.FAIL
}

fun GameContext.fail(errors: Collection<GameError>) {
    addErrors(errors)
    commandState = CommandState.FAIL
}

fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = GameError(
    code = "validation-$field-$violationCode",
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorSystem(
    violationCode: String,
    level: LogLevel = LogLevel.ERROR,
    e: Throwable,
) = GameError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Please retry later",
    level = level,
    exception = e,
)
