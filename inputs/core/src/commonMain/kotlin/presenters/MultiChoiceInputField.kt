@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import kollections.List
import kollections.Set
import presenters.validation.Validateable
import kotlin.js.JsExport

interface MultiChoiceInputField<O> : ChoiceField<O>, SerializableLiveDataList<O>, Validateable<List<O>> {
    val optionLabels: List<String>
    val optionValues: List<String>

    val selectedValues: Set<String>

    val selectedItems: List<O>

    val selectedOptions: List<Option>

    val options: List<Option>

    val optionsWithSelectLabel: List<Option>

    fun isSelected(item: O): Boolean

    fun isSelectedValue(v: String): Boolean

    fun isSelectedOption(o: Option): Boolean

    fun isSelectedLabel(l: String): Boolean

    fun addSelectedItem(item: O)

    fun addSelectedOption(o: Option)

    fun addSelectedValue(v: String)

    fun addSelectLabel(l: String)

    fun unselectOption(o: Option)

    fun unselectItem(i: O)

    fun unselectValue(v: String)

    fun unselectLabel(l: String)

    fun unselectAll()

    fun toggleSelectedValue(v: String)

    fun toggleSelectedOption(o: Option)

    fun toggleSelectedItem(i: O)

    fun toggleSelectedLabel(l: String)
}