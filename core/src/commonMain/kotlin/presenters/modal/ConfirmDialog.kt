@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.modal

import presenters.modal.builders.ConfirmDialogActionsBuilder
import presenters.modal.builders.ConfirmDialogBuildingBlock
import presenters.actions.SimpleAction
import presenters.modal.internal.ConfirmDialogImpl
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName

interface ConfirmDialog : Dialog<Nothing, Nothing> {
    val confirm: SimpleAction

    companion object {
        @JsName("create")
        @JvmName("create")
        operator fun invoke(
            heading: String,
            details: String,
            block: ConfirmDialogBuildingBlock
        ): ConfirmDialog = ConfirmDialogImpl(
            heading, details,
            actions = ConfirmDialogActionsBuilder().apply { block() }.actions,
            confirm = ConfirmDialogActionsBuilder().apply { block() }.confirmAction
        )
    }
}