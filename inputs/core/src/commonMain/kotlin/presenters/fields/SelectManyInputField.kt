@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kollections.Collection
import kollections.List
import kollections.iEmptyList
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.Live
import live.mutableLiveOf
import kotlin.js.JsExport

class SelectManyInputField<T : Any>(
    override val name: String,
    override val items: Collection<T>,
    val mapper: (T) -> Option,
    override val serializer: KSerializer<List<T>>,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY
) : MultiChoiceValuedField<T> {
    val optionLabels get() = options.map { it.label }.toIList()
    val optionValues get() = options.map { it.value }.toIList()

    override val output = mutableLiveOf<List<T>>(iEmptyList())
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    val selectedValues = mutableSetOf<String>()

    val selectedItems: List<T> get() = items.filter { selectedValues.contains(mapper(it).value) }.toIList()

    val selectedOptions get() = selectedItems.map(mapper).toIList()

    val options: List<Option>
        get() = items.map {
            val o = mapper(it)
            if (selectedValues.contains(o.value)) o.copy(selected = true) else o
        }.toIList()

    val optionsWithSelectLabel get() = (listOf(Option("Select $label", "")) + options).toIList()

    val selected get() = options.firstOrNull { it.selected }

    private fun updateValue() {
        val selectedItems = items.filter {
            val o = mapper(it)
            selectedValues.contains(o.value)
        }.toIList()
        output.value = if (selectedItems.isEmpty()) iEmptyList() else selectedItems
    }

    fun addSelectedItem(item: T) = addSelectedValue(mapper(item).value)

    fun addSelectedOption(o: Option) = addSelectedValue(o.value)

    fun addSelectedValue(v: String) {
        selectedValues.add(v)
        updateValue()
    }

    private fun findOptionWithLabel(l: String) = options.find { it.label == l }
    fun addSelectLabel(l: String) {
        findOptionWithLabel(l)?.let { addSelectedValue(it.value) }
    }

    fun unselectOption(o: Option) = unselectValue(o.value)

    fun unselectItem(i: T) = unselectValue(mapper(i).value)

    fun unselectValue(v: String) {
        selectedValues.remove(v)
        updateValue()
    }

    fun unselectLabel(l: String) {
        findOptionWithLabel(l)?.let { unselectValue(it.value) }
    }

    fun unselectAll() {
        selectedValues.clear()
        output.value = iEmptyList()
    }

    override fun clear() {
        unselectAll()
    }

    fun toggleSelectedValue(v: String) {
        if (selectedValues.contains(v)) {
            unselectValue(v)
        } else {
            addSelectedValue(v)
        }
    }

    fun toggleSelectedOption(o: Option) = toggleSelectedValue(o.value)

    fun toggleSelectedItem(i: T) = toggleSelectedValue(mapper(i).value)

    fun toggleSelectedLabel(l: String) {
        findOptionWithLabel(l)?.let { toggleSelectedValue(it.value) }
    }

    override fun validate(): ValidationResult {
        if (isRequired && output.value.isNullOrEmpty()) {
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