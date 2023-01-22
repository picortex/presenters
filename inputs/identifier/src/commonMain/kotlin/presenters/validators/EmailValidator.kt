package presenters.validators

import identifier.Email
import live.MutableLive
import presenters.InputFieldState
import presenters.OutputData

class EmailValidator(
    data: MutableLive<OutputData<String>>,
    feedback: MutableLive<InputFieldState>,
    label: String,
    isRequired: Boolean,
) : IdentifierValidator(data, feedback, label, isRequired) {
    override fun validate(value: String?) = purifyThen(value) {
        Email(it)
    }
}