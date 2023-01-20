@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.forms

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.nullable
import presenters.InputField
import presenters.fields.InputFieldState
import presenters.validation.Validateable0
import presenters.fields.ValuedField
import kotlin.js.JsExport

open class Fields(@PublishedApi internal val cache: MutableMap<String, InputField> = mutableMapOf()) {

    internal val all get() = cache.values

    internal fun encodedValuesToJson(codec: StringFormat) : String  = valuesToBeSubmitted.associate {
        it.name to it
    }.toList().joinToString(prefix = "{", postfix = "\n}") { (key, field) ->
        TODO()
//        val serializer = field.serializer as KSerializer<Any>
//        """${"\n"}    "$key": ${codec.encodeToString(serializer.nullable, field.data.value.output)}"""
    }

    internal val allInvalid get() = valuesToBeSubmitted.filter {
        TODO()
//        it.feedback.value is InputFieldState.Error
    }

    private val valueFields get() = cache.values.filterIsInstance<ValuedField<*>>()

    internal val valuesToBeSubmitted
        get() = valueFields.filterNot {
            TODO()
//            !it.isRequired && (it.data.value.output == null || it.data.value.output.toString().isBlank())
        }

    fun validate() {
        all.filterIsInstance<Validateable0>().forEach {
            it.validateSettingInvalidsAsErrors()
        }
//        valuesToBeSubmitted.forEach {
//            when (it) {
//                is SingleChoiceValuedField<*> -> it.validateSettingInvalidsAsErrors()
//                is RangeValuedField<*, *> -> it.validateSettingInvalidsAsErrors()
//                is SingleValuedField<*, *> -> it.validateSettingInvalidsAsErrors()
//                is MultiChoiceValuedField<*> -> it.validateSettingInvalidsAsErrors()
//            }
//        }
    }

    fun clearAll() {
        valueFields.forEach { it.clear() }
    }
}