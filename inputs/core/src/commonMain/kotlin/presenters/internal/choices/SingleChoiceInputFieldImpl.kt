package presenters.internal.choices

import kollections.Collection
import kollections.List
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.mutableLiveOf
import presenters.Label
import presenters.Option
import presenters.SingleChoiceInputField
import presenters.InputFieldState
import presenters.internal.OutputData
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class SingleChoiceInputFieldImpl<T>(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label = Label(name, isRequired),
    override val items: Collection<T>,
    override val hint: String,
    private val mapper: (T) -> Option,
    private val value: T? = null,
    override val isReadonly: Boolean,
    override val serializer: KSerializer<T>
) : SingleChoiceInputField<T> {

    override val data = mutableLiveOf(OutputData(value))

    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override val selectedItem: T? get() = data.value.output

    override val selectedOption: Option? get() = selectedItem?.let(mapper)?.copy(selected = true)

    override fun options(withSelect: Boolean): List<Option> = (if (withSelect) {
        listOf(Option("Select ${label.capitalizedWithoutAstrix()}", ""))
    } else {
        emptyList()
    } + items.toList().map {
        val o = mapper(it)
        if (it == data.value) o.copy(selected = true) else o
    }).toIList()

    override fun select(option: Option) = selectValue(option.value)

    override fun selectValue(optionValue: String) {
        data.value = OutputData(items.find { mapper(it).value == optionValue })
    }

    override fun selectLabel(optionLabel: String) {
        val opt = items.map(mapper).find { it.label == optionLabel }
        if (opt != null) select(opt)
    }

    override fun selectItem(item: T) = select(mapper(item))

    override fun unselect() {
        data.value = OutputData(null)
    }

    override fun clear() {
        unselect()
    }

    private val validator = RequirementValidator(data,feedback, label.capitalizedWithoutAstrix(), isRequired)

    override fun validate(value: T?) = validator.validate(data.value.output)
    override fun validateSettingInvalidsAsWarnings(value: T?) = validator.validateSettingInvalidsAsWarnings(data.value.output)
    override fun validateSettingInvalidsAsErrors(value: T?) = validator.validateSettingInvalidsAsErrors(data.value.output)
}