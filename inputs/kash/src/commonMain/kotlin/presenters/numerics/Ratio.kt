@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.numerics

import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.math.roundToInt

@Serializable
data class Ratio(
    val value: Double
) {
    companion object {
        fun from(value: Number) = Ratio(value.toDouble())
    }

    val asPercentageInt = value.roundToInt() * 100

    val asPercentageDouble = value * 100.0

    val inPercentage: Percentage = Percentage(value * 100)

    fun toPercentage() = Percentage(value * 100)
}
