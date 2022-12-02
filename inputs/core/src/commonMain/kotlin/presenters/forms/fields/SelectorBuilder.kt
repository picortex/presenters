package presenters.forms.fields

import presenters.fields.DropDownInputField
import presenters.forms.Fields
import kotlin.reflect.KProperty

@Deprecated("In favour of selectSingle")
inline fun Fields.selector(
    name: String? = null,
    label: String? = name,
    vararg options: DropDownInputField.Option,
) = getOrCreate { property ->
    DropDownInputField(
        name = name ?: property.name,
        label = label ?: property.name,
        options = *options
    )
}

@Deprecated("In favour of selectSingle")
inline fun Fields.selector(
    name: KProperty<*>,
    label: String? = name.name,
    vararg options: DropDownInputField.Option,
) = selector(name.name, label, *options)