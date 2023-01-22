package presenters.internal.text

import presenters.Label
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator
import presenters.internal.validators.TextValidator

@PublishedApi
internal class TextInputFieldImpl(
    name: String,
    isRequired: Boolean,
    label: Label,
    hint: String,
    isReadonly: Boolean,
    maxLength: Int?,
    minLength: Int?,
    value: String?,
    validator: ((String?) -> Unit)?,
) : AbstractBasicTextInputField(name, isRequired, label, hint, isReadonly, maxLength, minLength, value) {
    override val tv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        TextValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired, maxLength, minLength),
        LambdaValidator(data, feedback, validator)
    )
}