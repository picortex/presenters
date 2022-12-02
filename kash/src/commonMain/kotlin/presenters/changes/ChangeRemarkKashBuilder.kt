@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.changes

import kash.Money
import presenters.numerics.Percentage
import kotlin.js.JsExport
import kotlin.js.JsName

private const val CHANGE_MONEY_REMARK_OF = "changeMoneyRemarkOf"

@JsName(CHANGE_MONEY_REMARK_OF)
fun changeRemarkOf(
    previous: Money,
    current: Money,
    increaseFeeling: ChangeFeeling? = null,
    decreaseFeeling: ChangeFeeling? = null,
    fixedFeeling: ChangeFeeling? = null
): ChangeRemark<Money> {
    if (previous.currency != current.currency) {
        return ChangeRemark.Indeterminate
    }
    val diff = current - previous

    return when {
        previous.centsAsInt == 0 && diff.centsAsInt < 0 -> ChangeRemark.Decrease(
            pct = Percentage.ONE_HUNDRED,
            value = diff * -1,
            feeling = decreaseFeeling ?: ChangeRemark.Decrease.DEFAULT_FEELING
        )

        previous.centsAsInt != 0 && diff.centsAsInt < 0 -> ChangeRemark.Decrease(
            pct = Percentage.fromRatio(diff.centsAsInt * -1.0 / previous.centsAsInt),
            value = diff * -1,
            feeling = decreaseFeeling ?: ChangeRemark.Decrease.DEFAULT_FEELING
        )

        previous.centsAsInt == 0 && diff.centsAsInt > 0 -> ChangeRemark.Increase(
            pct = Percentage.ONE_HUNDRED,
            value = diff,
            feeling = increaseFeeling ?: ChangeRemark.Increase.DEFAULT_FEELING
        )

        previous.centsAsInt != 0 && diff.centsAsInt > 0 -> ChangeRemark.Increase(
            pct = Percentage.fromRatio(diff.centsAsInt.toDouble() / previous.centsAsInt),
            value = diff,
            feeling = increaseFeeling ?: ChangeRemark.Increase.DEFAULT_FEELING
        )

        else -> ChangeRemark.Fixed(
            at = previous,
            feeling = fixedFeeling ?: ChangeRemark.Fixed.DEFAULT_FEELING
        )
    }
}