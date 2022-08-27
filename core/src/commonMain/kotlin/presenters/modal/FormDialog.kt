@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.modal

import kotlinx.collections.interoperable.iListOf
import presenters.forms.Fields
import presenters.forms.BaseForm
import presenters.forms.FormActionsBuilder
import presenters.forms.FormActionsBuildingBlock
import presenters.modal.internal.FormDialogImpl
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName

interface FormDialog<out F : Fields, in P> : Dialog<F, P>, BaseForm<F, P> {
    companion object {
        @JsName("_ignore_constructor_1")
        @JvmName("create")
        operator fun <F : Fields, P> invoke(
            heading: String,
            details: String,
            fields: F,
            block: FormActionsBuildingBlock<P>
        ): FormDialog<F, P> = FormDialogImpl(
            heading, details, fields,
            actions = FormActionsBuilder<P>().apply { block() }.actions,
            submit = FormActionsBuilder<P>().apply { block() }.submitAction
        )

        @JsName("fromForm")
        @JvmName("fromForm")
        operator fun <F : Fields, P> invoke(form: BaseForm<F, P>): FormDialog<F, P> = FormDialogImpl(
            heading = form.heading,
            details = form.details,
            fields = form.fields,
            actions = iListOf(form.cancel),
            submit = form.submit,
        )
    }
}