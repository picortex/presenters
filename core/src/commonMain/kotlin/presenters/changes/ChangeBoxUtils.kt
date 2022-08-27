package presenters.changes

import kash.Money
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

@JvmName("toString")
fun <D> ChangeBox<D>?.toString() = when (this) {
    null -> ""
    else -> "$previous/$current"
}

@JvmSynthetic
@JvmName("toShortString")
fun ChangeBox<Money>?.toString() = when (this) {
    null -> ""
    else -> "${previous.toFormattedString()} / ${current.toFormattedString()}"
}