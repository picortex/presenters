@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.forms

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.nullable
import presenters.fields.InputField
import presenters.fields.InputFieldState
import presenters.fields.MultiChoiceValuedField
import presenters.fields.SingleChoiceValuedField
import presenters.fields.RangeValuedField
import presenters.fields.SingleValuedField
import presenters.fields.ValuedField
import kotlin.js.JsExport

open class Fields(@PublishedApi internal val cache: MutableMap<String, InputField> = mutableMapOf()) {

    internal val all get() = cache.values

    fun encodedValuesToJson(codec: StringFormat) = valuesToBeSubmitted.associate {
        it.name to it
    }.toList().joinToString(prefix = "{", postfix = "\n}") { (key, field) ->
        val serializer = field.serializer as KSerializer<Any>
        """${"\n"}    "$key": ${codec.encodeToString(serializer.nullable, field.output.value)}"""
    }

    internal val allInvalid get() = valuesToBeSubmitted.filter { it.feedback.value is InputFieldState.Error }

    private val valueFields get() = cache.values.filterIsInstance<ValuedField<*>>()

    internal val valuesToBeSubmitted
        get() = valueFields.filterNot {
            !it.isRequired && (it.output.value == null || it.output.value.toString().isBlank())
        }

    fun validate() {
        valuesToBeSubmitted.forEach {
            when (it) {
                is SingleChoiceValuedField<*> -> it.validateSettingInvalidsAsErrors()
                is RangeValuedField<*, *> -> it.validateSettingInvalidsAsErrors()
                is SingleValuedField<*, *> -> it.validateSettingInvalidsAsErrors()
                is MultiChoiceValuedField<*> -> it.validateSettingInvalidsAsErrors()
            }
        }
    }

    fun clearAll() {
        valueFields.forEach { it.clear() }
    }
}