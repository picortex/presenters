@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import kollections.List
import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.validation.Validateable
import kotlin.js.JsExport

interface SingleChoiceInputField<O> : ChoiceField<O>, Labeled, Hintable, Mutability, Requireble, LiveOutputData<O>, Validateable<O>, Clearable {
    val selectedItem: O?

    val selectedOption: Option?

    fun options(withSelect: Boolean = false): List<Option>

    fun select(option: Option)

    fun selectLabel(optionLabel: String)

    fun selectValue(optionValue: String)

    fun selectItem(item: O)

    fun unselect()
}