package presenters.changes

import kash.Money
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName

fun moneyChangeBoxOf(
    title: String,
    previous: Money,
    current: Money,
    details: String = "Updated now",
    increaseFeeling: ChangeFeeling? = null,
    decreaseFeeling: ChangeFeeling? = null,
    fixedFeeling: ChangeFeeling? = null,
    priority: Int = -1,
) = MoneyChangeBox(
    title = title,
    previous = previous,
    current = current,
    details = details,
    feeling = changeRemarkOf(previous, current, increaseFeeling, decreaseFeeling, fixedFeeling).feeling,
    priority
)

fun numberChangeBoxOf(
    title: String,
    previous: Number,
    current: Number,
    details: String = "Update now",
    increaseFeeling: ChangeFeeling? = null,
    decreaseFeeling: ChangeFeeling? = null,
    fixedFeeling: ChangeFeeling? = null,
    priority: Int = -1,
) = NumberChangeBox(
    title = title,
    previous = previous.toDouble(),
    current = current.toDouble(),
    details = details,
    feeling = changeRemarkOf(previous, current, increaseFeeling, decreaseFeeling, fixedFeeling).feeling,
    priority
)

inline fun <T> genericChangeBoxOf(
    title: String,
    previous: T,
    current: T,
    details: String,
    priority: Int = -1,
) = GenericChangeBox(
    title = title,
    previous = previous,
    current = current,
    details = details,
    feeling = ChangeFeeling.Unknown,
    priority
)

inline fun <reified T> changeBoxOf(
    title: String,
    previous: T,
    current: T,
    details: String,
    increaseFeeling: ChangeFeeling? = null,
    decreaseFeeling: ChangeFeeling? = null,
    fixedFeeling: ChangeFeeling? = null,
    priority: Int = -1,
): ChangeBox<T> = when {
    previous is Money && current is Money -> moneyChangeBoxOf(title, previous, current, details, increaseFeeling, decreaseFeeling, fixedFeeling, priority) as ChangeBox<T>
    previous is Number && current is Number -> numberChangeBoxOf(title, previous, current, details, increaseFeeling, decreaseFeeling, fixedFeeling, priority) as ChangeBox<T>
    else -> genericChangeBoxOf(title, previous, current, details, priority)
}