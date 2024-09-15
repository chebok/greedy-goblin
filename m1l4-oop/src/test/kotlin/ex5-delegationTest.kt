import org.junit.Test
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.test.assertEquals

internal class DelegationKtTest {
    @Test
    fun test() {
        val exampleA = DelegateExampleA()
        val exampleB = DelegateExampleB()

        println(exampleA.casualProperty)
        println(exampleA.importantProperty)
        assertEquals(exampleA.casualProperty, 20)

        println(exampleA.lazyVal)
        assertEquals(exampleA.lazyVal, 42)

        println(exampleB.specialValue)
        assertEquals(exampleA.casualProperty, exampleB.specialValue)
    }

    private class ConstValue(private val value: Int) : ReadWriteProperty<Any?, Int> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            // Используем both `thisRef` и `property` для кастомной логики
            return when {
                // Если объект ExampleB и имя свойства specialValue, вернём defaultValue * 2
                thisRef is DelegateExampleB && property.name == "specialValue" -> value * 2

                // Если имя свойства "importantProperty", вернём value * 10
                property.name == "importantProperty" -> value * 10

                // Если объект ExampleA, удвоим значение
                thisRef is DelegateExampleA -> value * 2

                // Иначе возвращаем дефолтное значение
                else -> value
            }
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            TODO("Not yet implemented")
        }
    }

    private class DelegateExampleA {
        val casualProperty by ConstValue(10)
        val importantProperty by ConstValue(10)
        val lazyVal by lazy {
            println("calculate...")
            42
        }
    }

    private class DelegateExampleB {
        val specialValue by ConstValue(10)
    }

}
