package ru.otus.otuskotlin.coroutines.homework.hard

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import ru.otus.otuskotlin.coroutines.homework.hard.dto.Dictionary
import java.io.File
import kotlin.test.Test

class HWHard {
    @Test
    fun hardHw(): Unit = runBlocking {
        val dictionaryApi = DictionaryApi()
        val words = FileReader.readFile().split(" ", "\n").toSet()

        val dictionaries = findWords(dictionaryApi, words, Locale.EN)

        dictionaries.filterNotNull().map { dictionary ->
            print("For word ${dictionary.word} i found examples: ")
            println(
                dictionary.meanings
                    .mapNotNull { definition ->
                        val r = definition.definitions
                            .mapNotNull { it.example.takeIf { it?.isNotBlank() == true } }
                            .takeIf { it.isNotEmpty() }
                        r
                    }
                    .takeIf { it.isNotEmpty() }
            )
        }
    }

    private suspend fun findWords(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ): List<Dictionary?> {
        val semaphore = Semaphore(10)
        val scope = CoroutineScope(Dispatchers.IO)
        // make some suspensions and async
        return words.map { word ->
            scope.async {
                semaphore.withPermit {
                    if (word == "guy") throw IllegalArgumentException("Forbidden word $word")
                    dictionaryApi.findWord(locale, word)
                }
            }
        }.mapNotNull { runCatching { it.await() }.getOrNull() }
    }



    object FileReader {
        fun readFile(): String =
            File(
                this::class.java.classLoader.getResource("words.txt")?.toURI()
                    ?: throw RuntimeException("Can't read file")
            ).readText()
    }
}
