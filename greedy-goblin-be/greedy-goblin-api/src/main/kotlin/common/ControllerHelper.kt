package io.greedy.goblin.api.common

import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.asGameError
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.models.GameCommand
import kotlinx.datetime.Clock
import toLog
import kotlin.reflect.KClass

suspend inline fun <T> IAppSettings.controllerHelper(
    crossinline getRequest: suspend GameContext.() -> Unit,
    crossinline toResponse: suspend GameContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx =
        GameContext(
            startTime = Clock.System.now(),
        )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
        )
        ctx.commandState = CommandState.FAIL
        ctx.errors.add(e.asGameError())
        processor.exec(ctx)
        if (ctx.command == GameCommand.NONE) {
            ctx.command = GameCommand.START
        }
        ctx.toResponse()
    }
}
