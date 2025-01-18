package io.greedy.goblin.api

import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.repo.inmemory.GameRepoInMemory
import io.greedy.goblin.repo.inmemory.PlayerActionRepoInMemory
import io.greedy.goblin.repo.inmemory.SceneRepoInMemory
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun `root endpoint`() =
        testApplication {
            application {
                module(
                    AppSettings(
                        corSettings =
                            CorSettings(
                                gameRepo = GameRepoInMemory(),
                                sceneRepo = SceneRepoInMemory(),
                                playerActionRepo = PlayerActionRepoInMemory(),
                            ),
                    ),
                )
            }
            val response = client.get("/")
            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals("Hello, world!", response.bodyAsText())
        }
}
