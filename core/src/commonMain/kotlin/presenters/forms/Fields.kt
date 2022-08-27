@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.forms

import presenters.fields.InputField
import presenters.fields.InputFieldState
import presenters.fields.SingleValuedField
import kotlin.js.JsExport

open class Fields(internal val cache: MutableMap<String, InputField> = mutableMapOf()) {

    internal val all get() = cache.values

    internal val valuesInJson
        get() = valuesToBeSubmitted.associate {
            it.name to it.value
        }.map { (key, value) -> key to value }.joinToString(prefix = "{", postfix = "\n}") { (key, value) ->
            """${"\n"}    "$key": ${if (value != null) """"$value"""" else null}"""
        }

    internal val allInvalid get() = valuesToBeSubmitted.filter { it.feedback.value is InputFieldState.Error }

    private val valueFields get() = cache.values.filterIsInstance<SingleValuedField<*>>()

    internal val valuesToBeSubmitted
        get() = valueFields.filterNot {
            !it.isRequired && (it.value == null || it.value.toString().isBlank())
        }

    fun validate() {
        valuesToBeSubmitted.forEach { it.validateWithFeedback() }
    }

    fun clearAll() {
        valueFields.forEach { it.clear() }
    }
}