package presenters.collections

fun <D> Table<D>.renderToConsole(gap: Int = 4) = println(renderToString(gap))