package presenters.validators

import identifier.Phone
import live.MutableLive
import presenters.InputFieldState
import presenters.OutputData

class PhoneValidator(
    data: MutableLive<OutputData<String>>,
    feedback: MutableLive<InputFieldState>,
    label: String,
    isRequired: Boolean,
) : IdentifierValidator(data, feedback, label, isRequired) {
    override fun validate(value: String?) = purifyThen(value) {
        Phone(it)
    }
}