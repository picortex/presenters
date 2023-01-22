package presenters.validators

import identifier.Phone
import live.MutableLive
import presenters.InputFieldState
import presenters.Data

class PhoneValidator(
    data: MutableLive<Data<String>>,
    feedback: MutableLive<InputFieldState>,
    label: String,
    isRequired: Boolean,
) : IdentifierValidator(data, feedback, label, isRequired) {
    override fun validate(value: String?) = purifyThen(value) {
        Phone(it)
    }
}