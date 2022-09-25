@file:JsExport

package presenters.actions

import kotlin.js.JsExport

interface ActionsBuilder<out A, in H> {
    fun on(name: String, handler: H): A

    fun onView(handler: H) = on("View", handler)

    fun onDelete(handler: H) = on("Delete", handler)

    fun onCancel(handler: H) = on("Cancel", handler)

    fun onOk(handler: H) = on("Ok", handler)

    fun onYes(handler: H) = on("Yes", handler)

    fun onNo(handler: H) = on("No", handler)

    fun onRetry(handler: H) = on("Retry", handler)

    fun onGoBack(handler: H) = on("Go Back", handler)
}