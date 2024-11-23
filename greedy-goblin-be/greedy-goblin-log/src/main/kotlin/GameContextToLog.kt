import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.ActionId
import io.greedy.goblin.common.models.GameError
import io.greedy.goblin.common.models.GameId
import io.greedy.goblin.common.models.RequestId
import io.greedy.goblin.log.models.CommonLogModel
import io.greedy.goblin.log.models.ErrorLogModel
import io.greedy.goblin.log.models.GameLogModel
import kotlinx.datetime.Clock

fun GameContext.toLog(logId: String) =
    CommonLogModel(
        messageTime = Clock.System.now().toString(),
        logId = logId,
        source = "greedy-goblin",
        game = toGameLog(),
        errors = errors.map { it.toLog() },
    )

private fun GameContext.toGameLog(): GameLogModel? =
    GameLogModel(
        requestId = requestId.takeIf { it != RequestId.NONE }?.asString(),
        gameId = gameId.takeIf { it != GameId.NONE }?.asString(),
        playerAction = playerAction.takeIf { it != ActionId.NONE }?.asString(),
    ).takeIf { it != GameLogModel() }

private fun GameError.toLog() =
    ErrorLogModel(
        message = message.takeIf { it.isNotBlank() },
        code = code.takeIf { it.isNotBlank() },
        level = level.name,
    )
