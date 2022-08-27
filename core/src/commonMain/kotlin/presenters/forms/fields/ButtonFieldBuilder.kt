package presenters.forms.fields

import presenters.fields.*
import presenters.forms.Fields

inline fun Fields.button(
    text: String,
    name: String = text,
) = getOrCreate { ButtonInputField(text, name) }