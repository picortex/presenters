@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.changes

import presenters.numerics.Percentage
import kotlin.js.JsExport
import kotlin.jvm.JvmField

sealed class ChangeRemark<out T> {
    abstract val feeling: ChangeFeeling

    data class Increase<out T>(
        val pct: Percentage,
        val value: T,
        override val feeling: ChangeFeeling = DEFAULT_FEELING
    ) : ChangeRemark<T>() {
        companion object {
            @JvmField
            val DEFAULT_FEELING = ChangeFeeling.Good
        }
    }

    data class Decrease<out T>(
        val pct: Percentage,
        val value: T,
        override val feeling: ChangeFeeling = DEFAULT_FEELING
    ) : ChangeRemark<T>() {
        companion object {
            @JvmField
            val DEFAULT_FEELING = ChangeFeeling.Bad
        }
    }

    data class Fixed<out T>(
        val at: T,
        override val feeling: ChangeFeeling = DEFAULT_FEELING
    ) : ChangeRemark<T>() {
        val pct: Percentage by lazy { Percentage.ZERO }

        companion object {
            @JvmField
            val DEFAULT_FEELING = ChangeFeeling.Neutral
        }
    }

    object Indeterminate : ChangeRemark<Nothing>() {
        override val feeling: ChangeFeeling = ChangeFeeling.Unknown
    }

    val isIncrease by lazy { this is Increase }
    val asIncrease by lazy { this as Increase }

    val isDecrease by lazy { this is Decrease }
    val asDecrease by lazy { this as Decrease }

    val isFixed by lazy { this is Fixed }
    val asFixed by lazy { this as Fixed }

    val isIndeterminate by lazy { this is Indeterminate }
    val asIndeterminate by lazy { this as Indeterminate }
}