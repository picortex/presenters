@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlin.js.JsExport
import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.toInteroperableList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import presenters.fields.internal.AbstractValuedField

class SelectSingleInputField<T : Any>(
    override val name: String,
    val items: Collection<T>,
    val mapper: (T) -> Option,
    override val serializer: KSerializer<T>,
    override val label: String = name.replaceFirstChar { it.uppercase() },
    override val defaultValue: T? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) : AbstractValuedField<T>(name, label, defaultValue, isReadonly, isRequired, ValuedField.DEFAULT_VALIDATOR) {
    val optionLabels get() = options.map { it.label }.toInteroperableList()
    val optionValues get() = options.map { it.value }.toInteroperableList()

    var selectedValue: String? = null
        get() = field ?: defaultValue?.let(mapper)?.value

    val selectedItem: T? get() = items.firstOrNull { mapper(it).value == selectedValue }

    val selectedOption: Option? get() = selectedItem?.let(mapper)?.copy(selected = true)

    val options: List<Option>
        get() = items.map {
            val o = mapper(it)
            if (o.value == selectedValue) o.copy(selected = true) else o
        }.toInteroperableList()

    val optionsWithSelectLabel get() = (listOf(Option("Select $label", "")) + options).toInteroperableList()

    val selected get() = options.firstOrNull { it.selected }

    fun selectOption(o: Option) = selectValue(o.value)

    fun selectValue(v: String) {
        selectedValue = v
        value = items.find { mapper(it).value == v }
    }

    fun selectLabel(l: String) {
        val opt = items.map(mapper).find { it.label == l }
        if (opt != null) selectOption(opt)
    }

    fun selectItem(item: T) = selectOption(mapper(item))

    fun unselect() {
        selectedValue = null
        value = null
    }

    override fun validate(value: T?) {
        if (isRequired && value == null) {
            throw IllegalArgumentException("$label is required")
        }
    }
}