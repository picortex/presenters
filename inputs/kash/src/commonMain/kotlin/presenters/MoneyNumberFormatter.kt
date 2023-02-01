package presenters

import kash.Currency
import kash.Money

object MoneyNumberFormatter : Formatter<Double> {
    override fun invoke(value: Double?): String? {
        val v = value ?: return null
        val money = Money(v, Currency.USD)
        return money.toFormattedString(
            prefix = "",
            abbreviate = false
        )
    }
}