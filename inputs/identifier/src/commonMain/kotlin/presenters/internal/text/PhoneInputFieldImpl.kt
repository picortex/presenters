package presenters.internal.text

import presenters.Label
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator
import presenters.internal.validators.TextValidator
import presenters.validators.EmailValidator
import presenters.validators.PhoneValidator

@PublishedApi
internal class PhoneInputFieldImpl(
    name: String,
    isRequired: Boolean = false,
    label: Label = Label(name, isRequired),
    hint: String = label.capitalizedWithAstrix(),
    isReadonly: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    value: String? = null,
    validator: ((String?) -> Unit)? = null,
) : AbstractBasicTextInputField(name, isRequired, label, hint, isReadonly, maxLength, minLength, value) {
    override val tv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        TextValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired, maxLength, minLength),
        PhoneValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )
}