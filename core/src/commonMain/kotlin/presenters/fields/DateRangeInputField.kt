@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import krono.LocalDate
import presenters.fields.internal.AbstractRangeValuedField
import presenters.fields.internal.AbstractSingleValuedField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

@JsExport
class DateRangeInputField(
    override val name: String,
    override val label: String = name,
    override val defaultValue: Range<LocalDate>? = ValuedField.DEFAULT_VALUE,
    override val limit: Range<LocalDate>? = RangeValuedField.DEFAULT_LIMIT,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    override val validator: ((Range<LocalDate>?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : AbstractRangeValuedField<LocalDate>(name, label, defaultValue, limit, isReadonly, isRequired, validator) {
    @JsName("_ignore_fromPropery")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        defaultValue: Range<LocalDate>? = ValuedField.DEFAULT_VALUE,
        limit: Range<LocalDate>? = RangeValuedField.DEFAULT_LIMIT,
        isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
        isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
        validator: ((Range<LocalDate>?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
    ) : this(name.name, label, defaultValue, limit, isReadonly, isRequired, validator)

    var startIsoString: String?
        get() = start?.toIsoString()
        set(iso) = if (iso == null) {
            start = null
        } else try {
            start = LocalDate(iso)
        } catch (err: Throwable) {
            start = null
            feedback.value = InputFieldState.Warning("Invalid date $iso", err)
        }

    var endIsoString: String?
        get() = end?.toIsoString()
        set(iso) = if (iso == null) {
            end = null
        } else try {
            end = LocalDate(iso)
        } catch (err: Throwable) {
            end = null
            feedback.value = InputFieldState.Warning("Invalid date $iso", err)
        }
}