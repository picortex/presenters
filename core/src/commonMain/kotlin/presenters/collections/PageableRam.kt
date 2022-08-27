@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

@JsExport
interface PageableRam<T> {
    fun read(page: Int, capacity: Int): Page<T>
    fun readOrNull(page: Int, capacity: Int): Page<T>?
    fun write(page: Page<T>): Page<T>?
}