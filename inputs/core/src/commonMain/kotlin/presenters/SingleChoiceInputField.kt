@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import kollections.List
import presenters.fields.properties.Clearable
import presenters.fields.properties.Hintable
import presenters.fields.properties.Labeled
import presenters.fields.properties.Mutability
import presenters.fields.properties.Requireble
import presenters.validation.Validateable0
import kotlin.js.JsExport

interface SingleChoiceInputField<O> : ChoiceField<O>, Labeled, Hintable, Mutability, Requireble, LiveOutputData<O>, Validateable0, Clearable {
    val selectedItem: O?

    val selectedOption: Option?

    fun options(withSelect: Boolean = false): List<Option>

    fun select(option: Option)

    fun selectLabel(optionLabel: String)

    fun selectValue(optionValue: String)

    fun selectItem(item: O)

    fun unselect()
}