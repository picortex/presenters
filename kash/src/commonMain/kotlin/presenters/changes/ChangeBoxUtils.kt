package presenters.changes

import kotlin.jvm.JvmName

@JvmName("toString")
fun <D> ChangeBox<D>?.toString() = when (this) {
    null -> ""
    else -> "$previous/$current"
}