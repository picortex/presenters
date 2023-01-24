package presenters.internal.choices

import kollections.Collection
import kollections.List
import kollections.iEmptyList
import kollections.toIList
import kollections.toISet
import kotlinx.serialization.KSerializer
import live.mutableLiveOf
import presenters.InputFieldState
import presenters.Label
import presenters.MultiChoiceInputField
import presenters.Option
import presenters.internal.OutputList
import presenters.internal.PlainDataListField
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class MultiChoiceInputFieldImpl<T : Any>(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val items: Collection<T>,
    private val mapper: (T) -> Option,
    private val value: Collection<T>?,
    override val serializer: KSerializer<List<T>>,
    override val isReadonly: Boolean,
    validator: ((List<T>) -> Unit)?
) : PlainDataListField<T>(value), MultiChoiceInputField<T> {

    override val cv by lazy {
        CompoundValidator(
            data, feedback,
            RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired)
        )
    }

    override val optionLabels get() = options.map { it.label }.toIList()

    override val optionValues get() = options.map { it.value }.toIList()

    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override val selectedValues get() = output.map { mapper(it).value }.toISet()

    override val selectedItems: List<T> get() = output

    override val selectedOptions get() = output.map(mapper)

    override val options: List<Option>
        get() = run {
            val selected = selectedValues
            items.map {
                val o = mapper(it)
                if (selected.contains(o.value)) o.copy(selected = true) else o
            }.toIList()
        }

    private val output get() = data.value.output
    override val optionsWithSelectLabel get() = (listOf(Option("Select $label", "")) + options).toIList()

    private fun updateValue() {
        val selectedItems = items.filter {
            val o = mapper(it)
            selectedValues.contains(o.value)
        }.toIList()
        data.value = OutputList(if (selectedItems.isEmpty()) iEmptyList() else selectedItems)
    }

    private fun Collection<T>.findItemWithLabel(l: String) = find { mapper(it).label == l }

    private fun Collection<T>.findItemWithOption(o: Option): T? = find { mapper(it) == o }

    private fun Collection<T>.findItemWithValue(v: String): T? = find { mapper(it).value == v }

    override fun addSelectedItem(item: T) {
        data.value = OutputList((output + item).toIList())
    }

    override fun addSelectedOption(o: Option) {
        val item = items.findItemWithOption(o) ?: return
        addSelectedItem(item)
    }

    override fun addSelectedValue(v: String) {
        val item = items.findItemWithValue(v) ?: return
        addSelectedItem(item)
    }

    override fun addSelectLabel(l: String) {
        val item = items.findItemWithLabel(l) ?: return
        addSelectedItem(item)
    }

    override fun isSelected(item: T): Boolean = output.contains(item)

    override fun isSelectedLabel(l: String) = output.findItemWithLabel(l) != null

    override fun isSelectedOption(o: Option) = output.findItemWithOption(o) != null

    override fun isSelectedValue(v: String) = output.findItemWithValue(v) != null

    override fun unselectOption(o: Option) {
        val item = output.findItemWithOption(o) ?: return
        unselectItem(item)
    }

    override fun unselectItem(i: T) {
        data.value = OutputList((output - i).toIList())
    }

    override fun unselectValue(v: String) {
        val item = output.findItemWithValue(v) ?: return
        unselectItem(item)
    }

    override fun unselectLabel(l: String) {
        val item = output.findItemWithLabel(l) ?: return
        unselectItem(item)
    }

    override fun unselectAll() {
        data.value = OutputList(iEmptyList())
    }

    override fun clear() {
        data.value = default
    }

    override fun toggleSelectedValue(v: String) {
        if (selectedValues.contains(v)) {
            unselectValue(v)
        } else {
            addSelectedValue(v)
        }
    }

    override fun toggleSelectedOption(o: Option) = toggleSelectedValue(o.value)

    override fun toggleSelectedItem(i: T) = if (isSelected(i)) {
        unselectItem(i)
    } else {
        addSelectedItem(i)
    }

    override fun toggleSelectedLabel(l: String) {
        if (isSelectedLabel(l)) {
            unselectLabel(l)
        } else {
            addSelectLabel(l)
        }
    }
}