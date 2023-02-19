package presenters.internal.numbers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import live.MutableLive
import live.mutableLiveOf
import presenters.BooleanInputField
import presenters.Label
import presenters.InputFieldState
import presenters.internal.OutputData
import presenters.internal.PlainDataField
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class BooleanInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val isReadonly: Boolean,
    value: Boolean?,
    validator: ((Boolean?) -> Unit)?,
) : PlainDataField<Boolean>(value), BooleanInputField {
    override val serializer: KSerializer<Boolean> = Boolean.serializer()

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )

    override fun toggle() = set(
        when (val value = data.value.output) {
            null -> true
            else -> !value
        }
    )
}