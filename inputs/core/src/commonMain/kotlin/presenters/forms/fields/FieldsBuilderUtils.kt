package presenters.forms.fields

import presenters.InputField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <F : InputField> Fields.getOrCreate(
    property: KProperty<*>,
    builder: () -> F
): F = getOrCreate(property.name, builder)

inline fun <F : InputField> Fields.getOrCreate(
    name: String,
    builder: () -> F
): F = cache.getOrPut(name) { builder() } as F