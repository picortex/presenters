package presenters.validators

import identifier.Email
import live.MutableLive
import presenters.InputFieldState
import presenters.Data

class EmailValidator(
    data: MutableLive<Data<String>>,
    feedback: MutableLive<InputFieldState>,
    label: String,
    isRequired: Boolean,
) : IdentifierValidator(data, feedback, label, isRequired) {
    override fun validate(value: String?) = purifyThen(value) {
        Email(it)
    }
}