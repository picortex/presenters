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
    override val label: String = name,
    val isReadonly: Boolean = false,
    val options: List<Option>
) : InputField {
    @JsName("from")
    constructor(
        name: String,
        label: String = name,
        isReadonly: Boolean = false,
        vararg options: Option
    ) : this(name, label, isReadonly, options.toIList())

    @JsName("_ignore_fromProperty")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        isReadonly: Boolean = false,
        options: List<Option>
    ) : this(name.name, label, isReadonly, options)

    val itemLabels get() = options.map { it.label }.toIList()
    val itemValues get() = options.map { it.value }.toIList()

    val optionsWithSelectLabel get() = (listOf(Option("Select $label", "")) + options).toIList()

    val selected get() = options.firstOrNull { it.selected }

    data class Option(
        val label: String,
        val value: String = label,
        val selected: Boolean = false
    )
}