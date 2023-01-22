package presenters.internal

import kollections.List
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.InputFieldState
import presenters.Label
import presenters.ListInputField
import presenters.Data
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class ListInputFieldImpl<E>(
    override val name: String,
    override val label: Label,
    override val hint: String,
    private val value: Collection<E>?,
    override val isReadonly: Boolean,
    override val isRequired: Boolean,
    override val maxItems: Int?,
    override val minItems: Int?,
    override val serializer: KSerializer<List<E>>
) : ListInputField<E> {
    private val default = OutputList(value)
    override val data = mutableLiveOf(default)
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    private val lv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired)
    )

    private val setter = OutputSetter(data as MutableLive<Data<List<E>>>, feedback, lv)
    override fun set(value: List<E>?) = setter.set(value)

    private val output get() = data.value.output
    override fun add(item: E) = set((output + item).toIList())

    override fun addAll(items: List<E>) = set((output + items).toIList())

    override fun remove(item: E) = set((output - item).toIList())

    override fun removeAll(items: List<E>) = set((output - items).toIList())

    override fun update(item: E, updater: () -> E) {
        val list = output.toMutableList()
        val idx = list.indexOf(item)
        list.remove(item)
        list.add(idx, updater())
        setter.set(list.toIList())
    }

    private val clearer = Clearer(default, data, feedback)
    override fun clear() = clearer.clear()

    override fun validate(value: List<E>?) = lv.validate(value)
    override fun validateSettingInvalidsAsErrors(value: List<E>?) = lv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsWarnings(value: List<E>?) = lv.validateSettingInvalidsAsWarnings(value)
}