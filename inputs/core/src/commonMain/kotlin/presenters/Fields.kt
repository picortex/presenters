@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.nullable
import presenters.properties.Clearable
import presenters.properties.Requireble
import presenters.validation.Validateable
import kotlin.js.JsExport

open class Fields(@PublishedApi internal val cache: MutableMap<String, InputField> = mutableMapOf()) {

    internal val all get() = cache.values

    internal fun encodedValuesToJson(codec: StringFormat): String = valuesToBeSubmitted().associateBy {
        it.name
    }.toList().joinToString(prefix = "{", postfix = "\n}") { (key, field) ->
        val serializer = field.serializer as KSerializer<Any>
        """${"\n"}    "$key": ${codec.encodeToString(serializer.nullable, field.data.value.output)}"""
    }

    private fun valuesToBeSubmitted() = all.filterIsInstance<SerializableLiveData<out Any?>>().filterNot {
        if (it is Requireble) {
            !it.isRequired && (it.data.value.output == null || it.data.value.output.toString().isBlank())
        } else {
            false
        }
    }

    /**
     * @return a [List] of invalid [InputField]s
     *
     * Note that if this is list is empty, it is safe to assume that all inputs are valid
     */
    fun validate(): List<SerializableLiveData<out Any?>> {
        all.filterIsInstance<Validateable<out Any?>>().forEach {
            it.validateSettingInvalidsAsErrors()
        }
        return valuesToBeSubmitted().filter {
            it is Validateable<out Any?> && it.feedback.value is InputFieldState.Error
        }
    }

    fun clearAll() = all.filterIsInstance<Clearable>().onEach { it.clear() }
}