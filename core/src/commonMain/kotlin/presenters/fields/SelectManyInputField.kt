@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlinx.collections.interoperable.*
import kotlinx.collections.interoperable.serializers.ListSerializer
import kotlinx.serialization.SerializationStrategy
import kotlin.js.JsExport
import presenters.fields.internal.AbstractSingleValuedField
import kotlin.collections.Collection
import kotlin.collections.listOf

class SelectManyInputField<T : Any>(
    override val name: String,
    internal val items: Collection<T>,
    internal val mapper: (T) -> Option,
    override val serializer: SerializationStrategy<List<T>>,
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
        value = null
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

    override fun validate(value: List<T>?) {
        if (isRequired && value.isNullOrEmpty()) {
            throw IllegalArgumentException("$label is required")
        }
    }
}