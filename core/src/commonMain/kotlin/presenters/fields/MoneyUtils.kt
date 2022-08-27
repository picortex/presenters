package presenters.fields

import kash.Money

fun Money.toInputValue() = toFormattedString(
    abbreviate = false,
    prefix = "",
    decimals = 2,
    thousandsSeparator = "",
    decimalSeparator = "."
).replace(" ", "")