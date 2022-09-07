@file:JsExport

package presenters.collections

import kotlin.js.JsExport

interface Selectable<in T> {
    val selector: SelectionManager<T>
}