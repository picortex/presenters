@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlinx.collections.interoperable.*
import kotlin.js.JsExport
import presenters.fields.internal.AbstractSingleValuedField
import kotlin.collections.Collection
import kotlin.collections.listOf

class SelectManyInputField<T : Any>(
    override val name: String,
    internal val items: Collection<T>,
    internal val mapper: (T) -> Option,
    override val label: String = name.replaceFirstChar { it.uppercase() },
    override val defaultValue: List<T>? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) : AbstractSingleValuedField<List<T>>(name, label, defaultValue, isReadonly, isRequired, ValuedField.DEFAULT_VALIDATOR) {
    val optionLabels get() = options.map { it.label }.toInteroperableList()
    val optionValues get() = options.map { it.value }.toInteroperableList()

    private val selectedValues = mutableSetOf<String>()

    val options: List<Option>
        get() = items.map {
            val o = mapper(it)
            if (selectedValues.contains(o.value)) o.copy(selected = true) else o
        }.toInteroperableList()

    val optionsWithSelectLabel get() = (listOf(Option("Select $label", "")) + options).toInteroperableList()

    val selected get() = options.firstOrNull { it.selected }

    private fun updateValue() {
        val selectedItems = items.filter {
            val o = mapper(it)
            selectedValues.contains(o.value)
        }.toInteroperableList()
        value = if (selectedItems.isEmpty()) null else selectedItems
    }

    fun selectItem(item: T) = selectValue(mapper(item).value)

    fun selectOption(o: Option) = selectValue(o.value)

    fun selectValue(v: String) {
        selectedValues.add(v)
        updateValue()
    }

    private fun findOptionWithLabel(l: String) = options.find { it.label == l }
    fun selectLabel(l: String) {
        findOptionWithLabel(l)?.let { selectValue(it.value) }
    }

    fun unselectOption(o: Option) = unselectValue(o.value)

    fun unselectValue(v: String) {
        selectedValues.remove(v)
        updateValue()
    }

    fun unselectLabel(l: String) {
        findOptionWithLabel(l)?.let { unselectValue(it.value) }
    }

    fun unselectAll() {
        selectedValues.clear()
        value = null
    }

    override fun validate(value: List<T>?) {
        if (isRequired && value.isNullOrEmpty()) {
            throw IllegalArgumentException("$label is required")
        }
    }
}