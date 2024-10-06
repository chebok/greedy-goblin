package ru.otus.otuskotlin.coroutines.homework.easy

fun findNumberInList(toFind: Int, numbers: IntArray): Int {
    Thread.sleep(2000)
    return numbers.find { it == toFind } ?: -1
}
