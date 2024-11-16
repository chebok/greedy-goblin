package io.goblingamble.api.v1.mappers

import GameContext
import io.goblingamble.api.v1.mappers.exceptions.UnknownRequestClass
import io.goblingamble.api.v1.models.*
import models.ActionId
import models.AppWorkMode
import models.GameCommand
import models.GameId
import stubs.AppStubs

fun GameContext.fromTransport(request: Any) {
    when (request) {
        is GameStartRequest -> fromTransport(request)
        is GameSceneRequest -> fromTransport(request)
        is GameActionRequest -> fromTransport(request)
        is GameEndRequest -> fromTransport(request)
        else -> throw UnknownRequestClass(request::class)
    }
}

fun GameContext.fromTransport(request: GameStartRequest) {
    command = GameCommand.START
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun GameContext.fromTransport(request: GameEndRequest) {
    command = GameCommand.END
    gameId = request.gameId.toGameId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun GameContext.fromTransport(request: GameActionRequest) {
    command = GameCommand.ACTION
    gameId = request.gameId.toGameId()
    playerAction = request.actionId.toActionId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun GameContext.fromTransport(request: GameSceneRequest) {
    command = GameCommand.GET_SCENE
    gameId = request.gameId.toGameId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun String?.toGameId() = this?.let { GameId(it) } ?: GameId.NONE

private fun String?.toActionId() = this?.let { ActionId(it) } ?: ActionId.NONE

private fun GameRequestDebug?.transportToWorkMode(): AppWorkMode =
    when (this?.mode) {
        GameRequestDebugMode.PROD -> AppWorkMode.PROD
        GameRequestDebugMode.TEST -> AppWorkMode.TEST
        GameRequestDebugMode.STUB -> AppWorkMode.STUB
        null -> AppWorkMode.PROD
    }

private fun GameRequestDebug?.transportToStubCase(): AppStubs =
    when (this?.stub) {
        GameRequestDebugStubs.SUCCESS -> AppStubs.SUCCESS
        GameRequestDebugStubs.SERVER_ERROR -> AppStubs.SERVER_ERROR
        GameRequestDebugStubs.INVALID_REQUEST -> AppStubs.INVALID_REQUEST
        GameRequestDebugStubs.GAME_NOT_ACTIVE -> AppStubs.GAME_NOT_ACTIVE
        GameRequestDebugStubs.BAD_ACTION -> AppStubs.BAD_ACTION
        null -> AppStubs.NONE
    }
