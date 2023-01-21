package presenters.internal

import kash.Money
import kash.MoneyFormatterOptions

private val DEFAULT_FORMATTER_OPTION = MoneyFormatterOptions(decimals = 0, abbreviate = false)

@Deprecated("Consider moving this to another package")
fun Money.toDefaultFormat() = toFormattedString(DEFAULT_FORMATTER_OPTION)