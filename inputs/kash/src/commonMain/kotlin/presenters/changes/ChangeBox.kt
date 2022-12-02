@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.changes

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
sealed class ChangeBox<out D> {
    abstract val title: String
    abstract val previous: D
    abstract val current: D
    abstract val details: String
    abstract val change: ChangeRemark<D>
    abstract val feeling: ChangeFeeling
    abstract val priority: Int

    val isMoneyChangeBox by lazy { this is MoneyChangeBox }
    val asMoneyChangeBox by lazy { this as MoneyChangeBox }

    val isNumberChangeBox by lazy { this is NumberChangeBox }
    val asNumberChangeBox by lazy { this as NumberChangeBox }

    val isGenericChangeBox by lazy { this is GenericChangeBox }
    val asGenericChangeBox by lazy { this as GenericChangeBox }
}