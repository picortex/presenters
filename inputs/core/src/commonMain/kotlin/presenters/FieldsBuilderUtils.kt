package presenters

import kotlin.reflect.KProperty

inline fun <F : InputField> Fields.getOrCreate(
    property: KProperty<Any?>,
    builder: () -> F
): F = getOrCreate(property.name, builder)

inline fun <F : InputField> Fields.getOrCreate(
    name: String,
    builder: () -> F
): F = cache.getOrPut(name) { builder() } as F