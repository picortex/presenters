@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "EqualsOrHashCode")

package presenters.modal.internal

import presenters.actions.SimpleAction
import kotlinx.collections.interoperable.List
import presenters.actions.GenericAction
import presenters.forms.Fields
import presenters.modal.FormDialog
import kotlin.js.JsExport

open class FormDialogImpl<out F : Fields, in P>(
    override val heading: String,
    override val details: String,
    override val fields: F,
    override val actions: List<SimpleAction>,
    override val submit: GenericAction<P>
) : FormDialog<F, P> {
    override val cancel by lazy {
        actions.firstOrNull {
            it.name.contentEquals("cancel", ignoreCase = true)
        } ?: error("No cancel action has been registered to FormDialog(heading=$heading)")
    }

    override fun validate() = fields.validate()

    override val isForm = true
    override val isConfirm = false

    override val asForm get() = this

    override fun equals(other: Any?): Boolean = other is FormDialogImpl<*, *> && other.heading == heading && other::class == this::class
}