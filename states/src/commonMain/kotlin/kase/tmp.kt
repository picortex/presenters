package kase

fun <T> test(state: EagerState<T>): Nothing = when (state) {
    is Loading -> TODO()
    is Success -> TODO()
    is Failure -> TODO()
}

fun <T> test(state: LazyState<T>) : Nothing = when(state) {
    Pending -> TODO()
    is Loading -> TODO()
    is Success -> TODO()
    is Failure -> TODO()
}

fun <D> test(state: Result<D>): Nothing = when(state) {
    is Failure -> TODO()
    is Success -> TODO()
}