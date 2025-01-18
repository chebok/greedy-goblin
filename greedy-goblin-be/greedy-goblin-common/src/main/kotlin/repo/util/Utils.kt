package io.greedy.goblin.common.repo.util

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.greedy.goblin.common.helpers.errorSystem
import io.greedy.goblin.common.models.GameError

suspend fun <T> safeDbCall(block: suspend () -> Either<List<GameError>, T>): Either<List<GameError>, T> =
    try {
        block()
    } catch (e: Throwable) {
        listOf(
            errorSystem(
                violationCode = "Database operation failed",
                e = e,
            ),
        ).left()
    }

fun <L, R> R?.toEither(ifNull: () -> L): Either<L, R> = this?.right() ?: ifNull().left()
