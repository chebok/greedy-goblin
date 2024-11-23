package io.greedy.goblin.api.v1.mappers

import io.greedy.goblin.api.v1.models.*
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.exceptions.UnknownGameCommand
import io.greedy.goblin.common.models.*

fun GameContext.toTransport(): Any =
    when (val cmd = command) {
        GameCommand.START -> toTransportGameStart()
        GameCommand.GET_SCENE -> toTransportGameScene()
        GameCommand.ACTION -> toTransportGameAction()
        GameCommand.END -> toTransportGameEnd()
        GameCommand.NONE -> throw UnknownGameCommand(cmd)
    }

fun GameContext.toTransportGameStart(): GameStartResponse =
    GameStartResponse(
        success = commandState.toResult(),
        message = if (errors.isEmpty()) "Game started successfully" else "Game failed to start",
        errors = errors.toTransportErrors(),
        gameId = gameId.asString(),
    )

fun GameContext.toTransportGameScene(): GameSceneResponse =
    GameSceneResponse(
        success = commandState.toResult(),
        message = if (errors.isEmpty()) "Scene retrieved successfully" else "Failed to retrieve scene",
        errors = errors.toTransportErrors(),
        scene = gameScene.toGameSceneView(),
    )

fun GameContext.toTransportGameAction(): GameActionResponse =
    GameActionResponse(
        success = commandState.toResult(),
        message = if (errors.isEmpty()) "Action performed successfully" else "Failed to perform action",
        errors = errors.toTransportErrors(),
    )

fun GameContext.toTransportGameEnd(): GameEndResponse =
    GameEndResponse(
        success = commandState.toResult(),
        message = if (errors.isEmpty()) "Game ended successfully" else "Failed to end the game",
        errors = errors.toTransportErrors(),
    )

private fun GameScene.toGameSceneView(): GameSceneView =
    GameSceneView(
        sceneId = sceneId.asString(),
        gameId = gameId.asString(),
        image = image,
        sceneTitle = description,
        playerActions = actions.map { it.toActionView() },
    )

private fun Action.toActionView(): GameSceneViewPlayerActionsInner =
    GameSceneViewPlayerActionsInner(
        id = id.asString(),
        title = title,
    )

private fun List<GameError>.toTransportErrors(): List<Error>? = this.map { it.toTransportError() }.takeIf { it.isNotEmpty() }

private fun GameError.toTransportError() =
    Error(
        code = code.takeIf { it.isNotBlank() },
        group = group.takeIf { it.isNotBlank() },
        message = message.takeIf { it.isNotBlank() },
    )

private fun CommandState.toResult(): Boolean =
    when (this) {
        CommandState.RUN -> true
        CommandState.FAIL -> false
        CommandState.FINISH -> true
        CommandState.NONE -> false
    }
