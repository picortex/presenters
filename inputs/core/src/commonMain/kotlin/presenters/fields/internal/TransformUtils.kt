package presenters.fields.internal

inline fun <I, O> transformOrNull(input: I, transformer: (I) -> O) = try {
    transformer(input)
} catch (err: Throwable) {
    null
}