@file:JsExport

package presenters.fields

import kotlin.js.JsExport

interface TransformedInput<in I, out O> {
    val transformer: (I?) -> O?
}