package io.greedy.goblin.api.v1.mappers

import io.greedy.goblin.api.v1.mappers.exceptions.UnknownRequestClass
import io.greedy.goblin.api.v1.models.*
import io.greedy.goblin.api.v1.ws.GameConnectRequest
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.stubs.AppStubs

fun GameContext.fromTransport(request: Any) {
    when (request) {
        is GameStartRequest -> fromTransport(request)
        is GameSceneRequest -> fromTransport(request)
        is GameActionRequest -> fromTransport(request)
        is GameEndRequest -> fromTransport(request)
        is GameConnectRequest -> fromTransport(request)
        else -> throw UnknownRequestClass(request::class)
    }
}

fun GameContext.fromTransport(request: GameStartRequest) {
    command = GameCommand.START
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun GameContext.fromTransport(request: GameConnectRequest) {
    command = GameCommand.CONNECT
    gameId = request.gameId.toGameId()
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
    playerId = PlayerId("f3df5d7a-1049-4cad-8f60-d510e79cac7e") // From token
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
        GameRequestDebugStubs.INVALID_REQUEST -> AppStubs.BAD_REQUEST
        null -> AppStubs.NONE
    }
