package presenters.fields.internal

import kollections.List
import presenters.fields.FormattableData
import presenters.fields.OutputData
import presenters.fields.OutputList
import presenters.fields.RawData

@PublishedApi
internal data class FormattableDataImpl<out I, out O>(
    override val raw: I?,
    override val formatted: String,
    override val output: O?
) : FormattableData<I, O>

@PublishedApi
internal class OutputListImpl<out D>(
    override val output: List<D>
) : OutputList<D>, List<D> by output

inline fun <O> OutputList(value: List<O>): OutputList<O> = OutputListImpl(value)

inline fun <O> RawData(value: O?): RawData<O, O> = FormattableDataImpl(value, "", value)

inline fun <O> OutputData(value: O?): OutputData<O> = FormattableDataImpl(value, "", value)

inline fun <I, O> FormattableData(
    raw: I?,
    formatted: String,
    output: O?
): FormattableData<I, O> = FormattableDataImpl(raw, formatted, output)