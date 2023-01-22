@file:JsExport

package presenters.properties

import kotlin.js.JsExport

interface Bounded<out B> {
    val max: B?
    val min: B?
}