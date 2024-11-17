package test.action

import fixture.client.Client
import io.kotest.core.spec.style.FunSpec
import test.action.v1.startGame

fun FunSpec.testApiV1(
    client: Client,
    prefix: String = "",
) {
    context("${prefix}v1") {
        test("Game start ok") {
            client.startGame()
        }
    }
}
