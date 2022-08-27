@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.forms

import presenters.actions.GenericAction
import presenters.actions.SimpleAction
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
interface BaseForm<out F : Fields, in P> {
    val heading: String
    val details: String
    val fields: F
    val cancel: SimpleAction
    val submit: GenericAction<P>

    fun validate()
}