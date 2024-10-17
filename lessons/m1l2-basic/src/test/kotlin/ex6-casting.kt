import kotlin.test.Test
import kotlin.test.assertEquals

class ComplexProcessTest {

    private fun complexProcess(input: Any?): Any {
        return when (input) {
            is Int -> "${input * 2}"
            is Double ->  "${input * 2}"
            is Float ->  "${input * 2}"
            is String ->  input.toList()
            is List<*> ->  input.map { complexProcess(it) }
            null -> "This is null"
            else -> "Unsupported type"
        }
    }

    fun mystery(value: Any?): Any {
        return value as? String? ?: "This is not a string"
    }

    @Test
    fun testCastTyping() {
        val elem1 = "kotlin"
        val elem2 = 42
        val elem3 = null
        assertEquals("kotlin", mystery(elem1))
        assertEquals("This is not a string", mystery(elem2))
        assertEquals("This is not a string", mystery(elem3))
    }

    @Test
    fun testProcessInt() {
        val result = complexProcess(5)
        assertEquals("10", result)  // 5 * 2 = 10
    }

    @Test
    fun testProcessDouble() {
        val result = complexProcess(2.5)
        assertEquals("5.0", result)  // 2.5 * 2 = 5.0
    }

    @Test
    fun testProcessString() {
        val result = complexProcess("Kotlin")
        assertEquals(listOf('K', 'o', 't', 'l', 'i', 'n'), result)  // Преобразование строки в список символов
    }

    @Test
    fun testProcessList() {
        val result = complexProcess(listOf(1, 2.0, "abc", null))
        assertEquals(listOf("2", "4.0", listOf('a', 'b', 'c'), "This is null"), result)  // Рекурсивная обработка списка
    }

    @Test
    fun testProcessNull() {
        val result = complexProcess(null)
        assertEquals("This is null", result)  // Обработка null
    }

    @Test
    fun testProcessUnsupportedType() {
        val result = complexProcess(mapOf("key" to "value"))
        assertEquals("Unsupported type", result)  // Неподдерживаемый тип данных
    }
}
