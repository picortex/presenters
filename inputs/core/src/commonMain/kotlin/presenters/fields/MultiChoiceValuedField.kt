@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kollections.List
import kollections.Set
import kollections.iEmptyList
import kollections.toIList
import live.Live
import live.mutableLiveOf
import kotlin.js.JsExport

interface MultiChoiceValuedField<out O> : ChoiceField<O>, ValuedField<List<O>> {
    val optionLabels: List<String>
    val optionValues: List<String>

    val selectedValues: Set<String>

    val selectedItems: List<O>

    val selectedOptions: List<Option>

    val options: List<Option>

    val optionsWithSelectLabel: List<Option>

    val selected: Option?

    fun addSelectedItem(item: @UnsafeVariance O)

    fun addSelectedOption(o: Option)

    fun addSelectedValue(v: String)

    fun addSelectLabel(l: String)

    fun unselectOption(o: Option)

    fun unselectItem(i: @UnsafeVariance O)

    fun unselectValue(v: String)

    fun unselectLabel(l: String)

    fun unselectAll()

    fun toggleSelectedValue(v: String)

    fun toggleSelectedOption(o: Option)

    fun toggleSelectedItem(i: @UnsafeVariance O)

    fun toggleSelectedLabel(l: String)
}