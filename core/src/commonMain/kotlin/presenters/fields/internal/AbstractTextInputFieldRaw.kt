@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.TextInputFieldRaw
import kotlin.js.JsExport

@Deprecated("In favour of TextBasedInputField")
abstract class AbstractTextInputFieldRaw(
    override val name: String,
    override val label: String = name,
    override val hint: String = label,
    value: String? = null,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val validator: (String?) -> String? = { it }
) : AbstractInputFieldWithValue<String?>(name, label, value, isReadonly, isRequired, validator), TextInputFieldRaw