package exceptions

import models.GameCommand

class UnknownGameCommand(
    command: GameCommand,
) : Throwable("Wrong command $command at mapping toTransport stage")
