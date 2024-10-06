package ru.otus.otuskotlin.coroutines

import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertEquals


class Ex10Additional {

    suspend fun fetchDataFromSource1(): Int {
        delay(1000)
        println("Fetching data from source1")
        return 5
    }
    suspend fun fetchDataFromSource2(): Int {
        delay(700)
        println("Fetching data from source2")
        return 10
    }
    suspend fun fetchDataFromSource3(): Int {
        delay(800)
        println("Fetching data from source3")
        throw Exception("Error fetching data from source3")
//        return 15
    }

    suspend private fun fetchAllData(): Int = coroutineScope {
        listOf(
            async { fetchDataFromSource1() },
            async { fetchDataFromSource2() },
            async { fetchDataFromSource3() }
        ).awaitAll().sum()
    }


    @Test
    fun firstProblem() = runBlocking(
        SupervisorJob() + CoroutineExceptionHandler { _, _ -> 0 }
    ) {
        val result = fetchAllData()
        println(result)
        assertEquals(result, 30)
    }


}