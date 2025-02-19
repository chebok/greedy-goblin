package io.greedy.goblin.common.repo.player

import arrow.core.Either
import io.greedy.goblin.common.models.GameError

typealias IPlayerActionDbResponse = Either<List<GameError>, Unit>
