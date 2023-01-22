package presenters.internal

import kollections.List
import kollections.iEmptyList
import kollections.toIList
import presenters.DataFormatted
import presenters.Data
import presenters.DataList

@PublishedApi
internal data class FormattedDataImpl<out I, out O>(
    override val input: I?,
    override val formatted: String,
    override val output: O?
) : DataFormatted<I, O>

@PublishedApi
internal class OutputListImpl<out D>(
    override val output: List<D>
) : DataList<D>, List<D> by output

inline fun <O> OutputList(value: Collection<O>?): DataList<O> = OutputListImpl(value?.toIList() ?: iEmptyList())

inline fun <O> OutputData(value: O?): Data<O> = FormattedDataImpl(value, "", value)

inline fun <I, O> FormattedData(
    raw: I?,
    formatted: String,
    output: O?
): DataFormatted<I, O> = FormattedDataImpl(raw, formatted, output)