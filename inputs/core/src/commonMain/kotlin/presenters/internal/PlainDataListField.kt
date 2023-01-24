package presenters.internal

import kollections.Collection
import kollections.List
import live.MutableLive
import live.mutableLiveOf
import presenters.CommonInputProperties
import presenters.Data
import presenters.LiveDataList
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.properties.Settable

abstract class PlainDataListField<O>(value: Collection<O>?) : CompoundValidateableInputField<List<O>>(), LiveDataList<O>, Settable<List<O>>, CommonInputProperties {
    protected val default = OutputList(value)
    override val data = mutableLiveOf(default)

    protected val setter by lazy { OutputSetter(data as MutableLive<Data<List<O>>>, feedback, cv) }
    override fun set(value: List<O>?) = setter.set(value)

    private val clearer by lazy { Clearer(default, data as MutableLive<Data<List<O>>>, feedback) }
    override fun clear() = clearer.clear()
}