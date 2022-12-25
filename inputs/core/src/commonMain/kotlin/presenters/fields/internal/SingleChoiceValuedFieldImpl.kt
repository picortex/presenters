package presenters.fields.internal

import kollections.Collection
import kollections.List
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.Option
import presenters.fields.SingleChoiceValuedField
import presenters.fields.SingleValuedField
import presenters.fields.Valid
import presenters.fields.ValidationResult

@PublishedApi
internal class SingleChoiceValuedFieldImpl<T : Any>(
    override val name: String,
    override val items: Collection<T>,
    override val mapper: (T) -> Option,
    override val serializer: KSerializer<T>,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    val defaultValue: T? = items.find { mapper(it).selected },
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY
) : SingleChoiceValuedField<T> {

    override val output = mutableLiveOf(defaultValue)
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override val selectedItem: T? get() = output.value

    override val selectedOption: Option? get() = selectedItem?.let(mapper)?.copy(selected = true)

    override fun options(withSelect: Boolean): List<Option> = (if (withSelect) {
        listOf(Option("Select ${label.capitalizedWithoutAstrix()}", ""))
    } else {
        emptyList()
    } + items.toList().map {
        val o = mapper(it)
        if (it == output.value) o.copy(selected = true) else o
    }).toIList()

    override fun select(option: Option) = selectValue(option.value)

    override fun selectValue(optionValue: String) {
        output.value = items.find { mapper(it).value == optionValue }
    }

    override fun selectLabel(optionLabel: String) {
        val opt = items.map(mapper).find { it.label == optionLabel }
        if (opt != null) select(opt)
    }

    override fun selectItem(item: T) = select(mapper(item))

    override fun unselect() {
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
            is Valid -> InputFieldState.Empty
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