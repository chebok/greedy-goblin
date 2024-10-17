package ru.otus.otuskotlin.coroutines

import kotlin.concurrent.thread
import kotlin.test.Test

class Ex1Thread {
    @Test
    fun thr() {
        println("Main thread: ${Thread.currentThread().name}") // Вывод имени основного потока

        thread {
            Thread.currentThread().setUncaughtExceptionHandler { _, e ->
                println("Caught exception in thread: ${e.message}")
            }

            println("Hello, thread started on: ${Thread.currentThread().name}")
            for (i in 1..10) {
                println("i = $i on ${Thread.currentThread().name}")
                Thread.sleep(100)
                if (i == 6) throw RuntimeException("Some error")
            }
        }.join()
        println("Thread complete")
    }
}
