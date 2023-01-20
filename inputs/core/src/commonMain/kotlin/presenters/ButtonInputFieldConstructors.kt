package presenters

import presenters.forms.Fields
import presenters.forms.fields.getOrCreate

fun Fields.button(
    label: String,
    name: String = label,
) = getOrCreate(name) { ButtonInputField(name, Label(label, false)) }