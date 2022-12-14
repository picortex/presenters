@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kollections.List
import kotlin.js.JsExport

interface SingleChoiceValuedField<O : Any> : ChoiceField<O>, ValuedField<O> {
    val mapper: (O) -> Option

    val selectedItem: O?

    val selectedOption: Option?

    fun options(withSelect: Boolean = false): List<Option>

    fun select(option: Option)

    fun selectLabel(optionLabel: String)

    fun selectValue(optionValue: String)

    fun selectItem(item: O)

    fun unselect()
}