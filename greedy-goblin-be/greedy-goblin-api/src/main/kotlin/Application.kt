package io.greedy.goblin.api

import io.greedy.goblin.api.plugins.*
import io.greedygoblin.plugins.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
//    EngineMain.main(args)

    // Явно загружаем конфигурацию из файла
    val config = ConfigLoader.load("application.yaml")

    // Получаем порт и хост для сервера из конфигурации
    val port = config.port
    val host = config.host

    // Явно запускаем сервер с конфигурированным портом и модулем
    embeddedServer(Netty, port = port, host = host) { module(config) }.start(wait = true)
}

fun Application.module(config: ApplicationConfig) {
    configureKoin(config)
    configureSecurity()
    configureHTTP()
    configureSerialization()
    initSceneUpdates()
    configureSockets()
    configureRouting()
    configureLogging()
}
