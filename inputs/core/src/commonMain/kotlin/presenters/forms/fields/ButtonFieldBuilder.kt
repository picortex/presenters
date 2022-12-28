package presenters.forms.fields

import presenters.fields.*
import presenters.forms.Fields

fun Fields.button(
    label: String,
    name: String = label,
) = getOrCreate(name) { ButtonInputField(name, InputLabel(label, false)) }