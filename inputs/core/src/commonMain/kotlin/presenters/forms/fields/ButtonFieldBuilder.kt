package presenters.forms.fields

import presenters.fields.*
import presenters.forms.Fields

inline fun Fields.button(
    label: String,
    name: String = label,
) = getOrCreate { ButtonInputField(name, InputLabel(label, false)) }