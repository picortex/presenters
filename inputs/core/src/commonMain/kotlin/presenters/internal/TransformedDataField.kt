package presenters.internal

import live.MutableLive
import live.mutableLiveOf
import presenters.CommonInputProperties
import presenters.DataFormatted
import presenters.LiveData
import presenters.LiveDataFormatted
import presenters.internal.utils.Clearer
import presenters.internal.utils.DataTransformer
import presenters.internal.utils.FormattedOutputSetter
import presenters.internal.utils.OutputSetter
import presenters.properties.Settable

abstract class TransformedDataField<I : Any, O : Any>(value: O?) : CompoundValidateableInputField<O>(), LiveDataFormatted<I, O>, Settable<I>, CommonInputProperties {
    protected val default = FormattedData<I, O>(null, "", value)
    override val data: MutableLive<DataFormatted<I, O>> = mutableLiveOf(default)

    abstract val transformer: DataTransformer<I, O>

    protected val setter by lazy { FormattedOutputSetter(data, feedback, transformer, cv) }
    override fun set(value: I?) = setter.set(value)

    private val clearer by lazy { Clearer(default, data, feedback) }
    override fun clear() = clearer.clear()
}