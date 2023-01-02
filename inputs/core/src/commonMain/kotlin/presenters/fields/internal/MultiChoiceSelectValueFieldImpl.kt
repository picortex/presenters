package presenters.fields.internal

import kollections.Collection
import kollections.List
import kollections.iEmptyList
import kollections.iMutableSetOf
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.validation.Invalid
import presenters.fields.MultiChoiceValuedField
import presenters.fields.Option
import presenters.fields.OutputList
import presenters.fields.SingleValuedField
import presenters.validation.Valid
import presenters.validation.ValidationResult

@PublishedApi
internal class MultiChoiceSelectValueFieldImpl<T : Any>(
    override val name: String,
    override val items: Collection<T>,
    val mapper: (T) -> Option,
    override val serializer: KSerializer<List<T>>,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY
) : MultiChoiceValuedField<T> {
    override val optionLabels get() = options.map { it.label }.toIList()
    override val optionValues get() = options.map { it.value }.toIList()

    override val data = mutableLiveOf<OutputList<T>>(OutputList(iEmptyList()))

    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override val selectedValues = iMutableSetOf<String>()

    override val selectedItems: List<T> get() = items.filter { selectedValues.contains(mapper(it).value) }.toIList()

    override val selectedOptions get() = selectedItems.map(mapper).toIList()

    override val options: List<Option>
        get() = items.map {
            val o = mapper(it)
            if (selectedValues.contains(o.value)) o.copy(selected = true) else o
        }.toIList()

    override val optionsWithSelectLabel get() = (listOf(Option("Select $label", "")) + options).toIList()

    override val selected get() = options.firstOrNull { it.selected }

    private fun updateValue() {
        val selectedItems = items.filter {
            val o = mapper(it)
            selectedValues.contains(o.value)
        }.toIList()
        data.value = OutputList(if (selectedItems.isEmpty()) iEmptyList() else selectedItems)
    }

    override fun addSelectedItem(item: T) = addSelectedValue(mapper(item).value)

    override fun addSelectedOption(o: Option) = addSelectedValue(o.value)

    override fun addSelectedValue(v: String) {
        selectedValues.add(v)
        updateValue()
    }

    private fun findOptionWithLabel(l: String) = options.find { it.label == l }

    override fun addSelectLabel(l: String) {
        findOptionWithLabel(l)?.let { addSelectedValue(it.value) }
    }

    override fun unselectOption(o: Option) = unselectValue(o.value)

    override fun unselectItem(i: T) = unselectValue(mapper(i).value)

    override fun unselectValue(v: String) {
        selectedValues.remove(v)
        updateValue()
    }

    override fun unselectLabel(l: String) {
        findOptionWithLabel(l)?.let { unselectValue(it.value) }
    }

    override fun unselectAll() {
        selectedValues.clear()
        data.value = OutputList(iEmptyList())
    }

    override fun clear() {
        unselectAll()
    }

    override fun toggleSelectedValue(v: String) {
        if (selectedValues.contains(v)) {
            unselectValue(v)
        } else {
            addSelectedValue(v)
        }
    }

    override fun toggleSelectedOption(o: Option) = toggleSelectedValue(o.value)

    override fun toggleSelectedItem(i: T) = toggleSelectedValue(mapper(i).value)

    override fun toggleSelectedLabel(l: String) {
        findOptionWithLabel(l)?.let { toggleSelectedValue(it.value) }
    }

    override fun validate(): ValidationResult {
        if (isRequired && data.value.isNullOrEmpty()) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} is required"))
        }
        return Valid
    }

    private fun validateSettingFeedback(body: (res: Invalid) -> InputFieldState): ValidationResult {
        val res = validate()
        feedback.value = when (res) {
            is Invalid -> body(res)
            Valid -> InputFieldState.Empty
        }
        return res
    }

    override fun validateSettingInvalidsAsWarnings() = validateSettingFeedback {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors() = validateSettingFeedback {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}