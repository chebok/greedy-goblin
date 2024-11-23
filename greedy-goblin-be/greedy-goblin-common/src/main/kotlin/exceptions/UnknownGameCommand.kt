package io.greedy.goblin.common.exceptions

import io.greedy.goblin.common.models.GameCommand

class UnknownGameCommand(
    command: GameCommand,
) : Throwable("Wrong command $command at mapping toTransport stage")
