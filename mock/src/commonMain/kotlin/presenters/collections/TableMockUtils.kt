package presenters.collections

fun <D> Table<D>.tabulateToConsole() = println(tabulateToString())