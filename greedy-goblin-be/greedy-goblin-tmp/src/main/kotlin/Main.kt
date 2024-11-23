package io.greedy.goblin.tmp

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*
import io.greedy.goblin.libs.logging.common.LogLevel
import io.greedy.goblin.libs.logging.jvm.mpLoggerLogback
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import toLog

suspend fun main() {
    val logger = mpLoggerLogback("app-tmp")
    while (true) {
        val ctx =
            GameContext(
                command = GameCommand.START,
                commandState = CommandState.RUN,
                workMode = AppWorkMode.STUB,
                startTime = Clock.System.now(),
                requestId = RequestId("tmp-request"),
                gameId = GameId("tmp-game"),
                errors =
                    mutableListOf(
                        GameError(
                            code = "tmp-error",
                            group = "tmp",
                            message = "tmp error message",
                            level = LogLevel.INFO,
                            exception = Exception("some exception"),
                        ),
                    ),
            )
        logger.info(
            msg = "tmp log string",
            data = ctx.toLog("tmp-app-log"),
        )
        delay(500)
    }
}
