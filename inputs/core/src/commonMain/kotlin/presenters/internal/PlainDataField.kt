package presenters.internal

import live.mutableLiveOf
import presenters.CommonInputProperties
import presenters.LiveData
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.properties.Settable

abstract class PlainDataField<O : Any>(value: O?) : CompoundValidateableInputField<O>(), LiveData<O>, Settable<O>, CommonInputProperties {
    protected val default = OutputData(value)
    override val data = mutableLiveOf(default)

    protected val setter by lazy { OutputSetter(data, feedback, cv) }
    override fun set(value: O?) = setter.set(value)

    private val clearer by lazy { Clearer(default, data, feedback) }
    override fun clear() = clearer.clear()
}