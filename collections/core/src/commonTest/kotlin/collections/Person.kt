package collections

import presenters.collections.columnsOf

data class Person(val name: String = "Andy", val age: Int = 12) {
    companion object {
        val List = List(25) { Person("Anderson $it", age = 15 + it) }

        fun columns() = columnsOf<Person> {
            selectable()
            column("No") { it.number.toString() }
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
    }
}