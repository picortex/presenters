@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION")

package presenters.collections

import presenters.collections.internal.RowImpl
import kotlin.js.JsExport

interface Row<out D> {
    val index: Int
    val item: D
    val number: Int

    companion object {
        operator fun <T> invoke(
            index: Int,
            item: T
        ): Row<T> = RowImpl(index, item)
    }
}