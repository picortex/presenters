@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlin.js.JsExport
import kotlin.js.JsName
import kollections.List
import kollections.toIList
import kotlin.reflect.KProperty

data class DropDownInputField(
    override val name: String,
    val isRequired: Boolean = false,
    override val label: InputLabel = InputLabel(name, isRequired),
    val isReadonly: Boolean = false,
    val options: List<Option>
) : InputField {
    @JsName("from")
    constructor(
        name: String,
        isRequired: Boolean = false,
        label: InputLabel = InputLabel(name, isRequired),
        isReadonly: Boolean = false,
        vararg options: Option
    ) : this(name, isRequired, label, isReadonly, options.toIList())

    val itemLabels get() = options.map { it.label }.toIList()
    val itemValues get() = options.map { it.value }.toIList()

    val optionsWithSelectLabel get() = (listOf(Option("Select $label", "")) + options).toIList()

    val selected get() = options.firstOrNull { it.selected }
}