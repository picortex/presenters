@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import kollections.List
import presenters.validation.Validateable
import kotlin.js.JsExport

interface SingleChoiceInputField<O> : ChoiceField<O>, SerializableLiveData<O>, Validateable<O> {
    val selectedItem: O?

    val selectedOption: Option?

    fun options(withSelect: Boolean = false): List<Option>

    fun select(option: Option)

    fun selectLabel(optionLabel: String)

    fun selectValue(optionValue: String)

    fun selectItem(item: O)

    fun unselect()
}