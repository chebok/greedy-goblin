package io.greedy.goblin.repo.postgres.entity

import io.greedy.goblin.common.models.PlayerState
import kotlinx.serialization.Serializable

@Serializable
enum class PlayerStateDb {
    WAITING_FOR_ACTION,
    ACTION_MADE,
}

fun PlayerStateDb.toDomain(): PlayerState =
    when (this) {
        PlayerStateDb.WAITING_FOR_ACTION -> PlayerState.WAITING_FOR_ACTION
        PlayerStateDb.ACTION_MADE -> PlayerState.ACTION_MADE
    }

fun PlayerState.toDb(): PlayerStateDb =
    when (this) {
        PlayerState.WAITING_FOR_ACTION -> PlayerStateDb.WAITING_FOR_ACTION
        PlayerState.ACTION_MADE -> PlayerStateDb.ACTION_MADE
    }
