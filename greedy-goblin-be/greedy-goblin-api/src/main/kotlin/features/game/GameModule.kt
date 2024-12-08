package io.greedy.goblin.api.features.game

import org.koin.dsl.module

val gameModule =
    module {

        single { GameController(get()) }
    }
