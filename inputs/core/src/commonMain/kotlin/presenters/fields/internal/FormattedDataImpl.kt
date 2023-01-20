package presenters.fields.internal

import kollections.List
import presenters.fields.FormattedData
import presenters.OutputData
import presenters.OutputList
import presenters.fields.TransformedData

@PublishedApi
internal data class FormattedDataImpl<out I, out O>(
    override val input: I?,
    override val formatted: String,
    override val output: O?
) : FormattedData<I, O>

@PublishedApi
internal class OutputListImpl<out D>(
    override val output: List<D>
) : OutputList<D>, List<D> by output

inline fun <O> OutputList(value: List<O>): OutputList<O> = OutputListImpl(value)

inline fun <O> RawData(value: O?): TransformedData<O, O> = FormattedDataImpl(value, "", value)

inline fun <I, O> RawData(input: I, output: O?): TransformedData<I, O> = FormattedDataImpl(input, "", output)

inline fun <O> OutputData(value: O?): OutputData<O> = FormattedDataImpl(value, "", value)

inline fun <I, O> FormattedData(
    raw: I?,
    formatted: String,
    output: O?
): FormattedData<I, O> = FormattedDataImpl(raw, formatted, output)