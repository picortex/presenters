@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kollections.Collection
import kollections.List
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.mutableLiveOf
import kotlin.js.JsExport

class SelectSingleInputField<T : Any>(
    override val name: String,
    override val items: Collection<T>,
    val mapper: (T) -> Option,
    override val serializer: KSerializer<T?>,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    val defaultValue: T? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY
) : SingleChoiceValuedField<T> {

    override val output = mutableLiveOf(defaultValue)
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    val optionLabels get() = options.map { it.label }.toIList()
    val optionValues get() = options.map { it.value }.toIList()

    val selectedValue: String? get() = output.value?.let(mapper)?.value

    val selectedItem: T? get() = output.value

    val selectedOption: Option? get() = selectedItem?.let(mapper)?.copy(selected = true)

    val options: List<Option>
        get() = items.map {
            val o = mapper(it)
            if (it == output.value) o.copy(selected = true) else o
        }.toIList()

    val optionsWithSelectLabel get() = (listOf(Option("Select ${label.capitalizedWithoutAstrix()}", "")) + options).toIList()

    fun selectOption(o: Option) = selectValue(o.value)

    fun selectValue(v: String) {
        output.value = items.find { mapper(it).value == v }
    }

    fun selectLabel(l: String) {
        val opt = items.map(mapper).find { it.label == l }
        if (opt != null) selectOption(opt)
    }

    fun selectItem(item: T) = selectOption(mapper(item))

    fun unselect() {
        output.value = null
    }

    override fun clear() {
        unselect()
    }

    override fun validate(): ValidationResult {
        if (isRequired && output.value == null) {
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