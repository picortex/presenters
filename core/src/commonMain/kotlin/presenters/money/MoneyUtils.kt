package presenters.money

import kash.Money
import kash.MoneyFormatterOptions

private val DEFAULT_FORMATTER_OPTION = MoneyFormatterOptions(decimals = 0, abbreviate = false)

fun Money.toDefaultFormat() = toFormattedString(DEFAULT_FORMATTER_OPTION)