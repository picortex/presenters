@file:JsExport

package presenters.scopes

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
sealed class View {
    @Serializable
    object TableView : View()

    @Serializable
    object ListView : View()

    val isTableView get() = this is TableView
    val isListView get() = this is ListView
}