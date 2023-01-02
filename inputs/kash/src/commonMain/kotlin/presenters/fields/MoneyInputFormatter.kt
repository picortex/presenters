package presenters.fields

import kash.Currency
import kash.Money

object MoneyInputFormatter : Formatter<Double> {
    override fun invoke(value: Double?): String? {
        val v = value ?: return null
        val money = Money.of(v, Currency.USD)
        return money.toFormattedString(
            prefix = "",
            abbreviate = false
        )
    }
}