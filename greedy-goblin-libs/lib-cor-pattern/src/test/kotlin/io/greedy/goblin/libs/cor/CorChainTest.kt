package io.greedy.goblin.libs.cor

import io.greedy.goblin.libs.cor.handlers.CorChain
import io.greedy.goblin.libs.cor.handlers.CorWorker
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CorChainTest {
    @Test
    fun `chain should execute workers`() =
        runTest {
            val createWorker = { title: String ->
                CorWorker<TestContext>(
                    title = title,
                    blockOn = { status == TestContext.CorStatuses.NONE },
                    blockHandle = { history += "$title; " },
                )
            }
            val chain =
                CorChain<TestContext>(
                    execs = listOf(createWorker("w1"), createWorker("w2")),
                    title = "chain",
                )
            val ctx = TestContext()
            chain.exec(ctx)
            assertEquals("w1; w2; ", ctx.history)
        }
}
