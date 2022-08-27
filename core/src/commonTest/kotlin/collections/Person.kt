package collections

data class Person(val name: String = "Andy", val age: Int = 12) {
    companion object {
        val List = List(25) { Person("Anderson $it", age = 15 + it) }
    }
}