package presenters.internal.utils

import presenters.fields.FormattedData
import presenters.fields.Formatter
import presenters.fields.internal.FormattedData

class DataTransformer<I : Any, O : Any>(
    val formatter: Formatter<O>?,
    val transformer: (I?) -> O?
) {
    fun toFormattedData(value: I?): FormattedData<I, O> {
        val o = transformer(value)
        return FormattedData(
            raw = value,
            formatted = formatted(value, o),
            output = o
        )
    }

    protected fun formatted(input: I?, output: O?): String = formatter?.invoke(output) ?: input?.toString() ?: ""
}