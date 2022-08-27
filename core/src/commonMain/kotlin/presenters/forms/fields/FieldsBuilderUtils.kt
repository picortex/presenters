package presenters.forms.fields

import presenters.fields.InputField
import presenters.forms.Fields
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : InputField> Fields.getOrCreate(
    builder: (property: KProperty<*>) -> T
) = ReadOnlyProperty<Fields, T> { _, property ->
    (cache[property.name] as? T) ?: run {
        builder(property).also { cache[property.name] = it }
    }
}