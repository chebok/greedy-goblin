package ru.otus.otuskotlin.flow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

/**
 * Deprecated. Каналы более не применяются.
 */
class ChannelTest {

    /**
     * Отправка и получение данных в/из каналов
     */
    @Test
    fun test1(): Unit = runBlocking {
        val channel = Channel<Int>()

        launch {
            for (x in 1..5) channel.send(x * x)
            channel.close()
        }

        for (value in channel) {
            println(value)
        }
        println("Done!")
    }

    /**
     * Параллельная отправка в каналы
     */
    @Test
    fun test2(): Unit = runBlocking {
        val channel = Channel<Int>()

        launch {
            for (x in 1..5) channel.send(x * x)
        }

        launch {
            for (x in 10..15) channel.send(x * x)
        }

        launch {
            delay(2000)
            channel.close()
        }

        for (value in channel) {
            println(value)
        }
    }

    /**
     * Параллельное извлечение из каналов
     */
    @Test
    fun test3(): Unit = runBlocking {
        val channel = Channel<Int>()

        launch {
            for (x in 1..5) channel.send(x * x)
        }

        launch {
            for (x in 10..15) channel.send(x * x)
        }

        launch {
            delay(2000)
            channel.close()
        }

        launch {
            for (value in channel) {
                println("First consumer: $value")
            }
        }

        launch {
            for (value in channel) {
                println("Second consumer: $value")
            }
        }
    }

    /**
     * Буферизация в каналах
     */
    @Test
    fun test4(): Unit = runBlocking {
        val channel = Channel<Int>(
            capacity = 5,
            onBufferOverflow = BufferOverflow.SUSPEND
        ) {
            // never call, because onBufferOverflow = SUSPEND
            println("Call for value: $it")
        }

        launch {
            for (x in 1..10) {
                val value = x * x
                channel.send(value)
                println("Send value: $value")
            }
        }

        launch {
            delay(11000)
            channel.close()
        }

        launch {
            for (value in channel) {
                println("Consumer: $value")
                delay(1000)
            }
        }
    }
}
