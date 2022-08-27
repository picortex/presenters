@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections.internal

import presenters.collections.Row
import kotlin.js.JsExport

data class RowImpl<out D>(
    override val index: Int,
    override val item: D,
    override val number: Int = index + 1
) : Row<D>