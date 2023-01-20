@file:JsExport
package presenters.fields.properties

import kotlin.js.JsExport
import kotlin.js.JsName

interface Settable<in V> {
    @JsName("setValue")
    fun set(value: V)
}