@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.forms

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.serializer
import presenters.fields.InputField
import presenters.fields.InputFieldState
import presenters.fields.SingleValuedField
import presenters.fields.internal.TextBasedValueField
import kotlin.js.JsExport

open class Fields(internal val cache: MutableMap<String, InputField> = mutableMapOf()) {

    internal val all get() = cache.values

    internal val valuesAsObjects
        get() = valuesToBeSubmitted.associate {
            it.name to it.value
        }.toMap()

    fun encodedValuesToJson(codec: StringFormat) = valuesToBeSubmitted.associate {
        it.name to it
    }.toList().joinToString(prefix = "{", postfix = "\n}") { (key, field) ->
        val serializer = field.serializer as KSerializer<Any>
        """${"\n"}    "$key": ${codec.encodeToString(serializer.nullable, field.value)}"""
    }

    internal val valuesInJson
        get() = valuesToBeSubmitted.associate {
            it.name to it
        }.toList().joinToString(prefix = "{", postfix = "\n}") { (key, field) ->
            val v = when (field) {
                is TextBasedValueField -> """"${field.value}""""
                else -> field.value
            }
            """${"\n"}    "$key": $v"""
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