package presenters.changes

import kash.Money

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