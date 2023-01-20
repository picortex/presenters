@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.forms

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.nullable
import presenters.InputField
import presenters.LiveOutputData
import presenters.fields.InputFieldState
import presenters.fields.properties.Clearable
import presenters.fields.properties.Requireble
import presenters.validation.Validateable0
import kotlin.js.JsExport

open class Fields(@PublishedApi internal val cache: MutableMap<String, InputField> = mutableMapOf()) {

    internal val all get() = cache.values

    internal fun encodedValuesToJson(codec: StringFormat): String = valuesToBeSubmitted.associateBy {
        it.name
    }.toList().joinToString(prefix = "{", postfix = "\n}") { (key, field) ->
        val serializer = field.serializer as KSerializer<Any>
        """${"\n"}    "$key": ${codec.encodeToString(serializer.nullable, field.data.value.output)}"""
    }

    internal val allInvalid
        get() = valuesToBeSubmitted.filter {
            it is Validateable0 && it.feedback.value is InputFieldState.Error
        }

    internal val valuesToBeSubmitted
        get() = all.filterIsInstance<LiveOutputData<*>>().filterNot {
            if (it is Requireble) {
                !it.isRequired && (it.data.value.output == null || it.data.value.output.toString().isBlank())
            } else {
                false
            }
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
        all.filterIsInstance<Clearable>().forEach { it.clear() }
    }
}