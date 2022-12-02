@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.changes

import presenters.numerics.Percentage
import kotlin.js.JsExport

fun changeRemarkOf(
    previous: Number,
    current: Number,
    increaseFeeling: ChangeFeeling? = null,
    decreaseFeeling: ChangeFeeling? = null,
    fixedFeeling: ChangeFeeling? = null
): ChangeRemark<Double> {
    val prev = previous.toDouble()
    val diff = current.toDouble() - prev

    return when {
        prev == 0.0 && diff < 0.0 -> ChangeRemark.Decrease(
            pct = Percentage.ONE_HUNDRED,
            value = diff * -1,
            feeling = decreaseFeeling ?: ChangeRemark.Decrease.DEFAULT_FEELING
        )
        prev != 0.0 && diff < 0.0 -> ChangeRemark.Decrease(
            pct = Percentage.fromRatio(-diff / prev),
            value = diff * -1,
            feeling = decreaseFeeling ?: ChangeRemark.Decrease.DEFAULT_FEELING
        )
        prev == 0.0 && diff > 0.0 -> ChangeRemark.Increase(
            pct = Percentage.ONE_HUNDRED,
            value = diff,
            feeling = increaseFeeling ?: ChangeRemark.Increase.DEFAULT_FEELING
        )
        prev != 0.0 && diff > 0.0 -> ChangeRemark.Increase(
            pct = Percentage.fromRatio(diff / prev),
            value = diff,
            feeling = increaseFeeling ?: ChangeRemark.Increase.DEFAULT_FEELING
        )
        else -> ChangeRemark.Fixed(
            at = prev,
            feeling = fixedFeeling ?: ChangeRemark.Fixed.DEFAULT_FEELING
        )
    }
}