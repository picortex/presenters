package presenters.internal.text

import presenters.Label
import presenters.TextInputField
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator
import presenters.internal.validators.TextValidator

@PublishedApi
internal class TextInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val isReadonly: Boolean,
    override val maxLength: Int?,
    override val minLength: Int?,
    value: String?,
    validator: ((String?) -> Unit)?,
) : AbstractBasicTextInputField(value), TextInputField {
    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        TextValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired, maxLength, minLength),
        LambdaValidator(data, feedback, validator)
    )
}