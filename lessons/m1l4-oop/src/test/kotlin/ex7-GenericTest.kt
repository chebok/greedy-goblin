import kotlin.test.Test
import kotlin.test.assertEquals

class GenericTest {
    @Test
    fun invariant() {
        assertEquals(3, (IntSome(1) + IntSome(2)).value)
        assertEquals(listOf(1, 2), (ListSome(listOf(1)) + ListSome(listOf(2))).value)
        println(ListSome(listOf(1)) + ListSome(listOf(2)))
    }

    @Test
    fun covariant() {
        assertEquals(3, CovariantCls().parse("3"))
    }

    @Test
    fun contravariant() {
        assertEquals("3", ContravariantCls().toStr(3))
        assertEquals("3", ContravariantCls().toStr(3))
    }

    private interface ISome<T: ISome<T>> {
        operator fun plus(other: T): T
    }

    private class IntSome(val value: Int): ISome<IntSome> {
        override fun plus(other: IntSome): IntSome = IntSome(value + other.value)
    }

    private class ListSome(val value: List<Int>): ISome<ListSome> {
        override fun plus(other: ListSome): ListSome = ListSome(value + other.value)

        override fun toString(): String {
            return value.toString()
        }
    }

    private interface IParse<out T> {
        fun parse(str: String): T
    }

    private class CovariantCls: IParse<Int> {
        override fun parse(str: String): Int = str.toInt()
    }

    private interface IToString<in T> {
        fun toStr(i: T): String
    }

    private class ContravariantCls: IToString<Int> {
        override fun toStr(i: Int): String = i.toString()
    }
}
