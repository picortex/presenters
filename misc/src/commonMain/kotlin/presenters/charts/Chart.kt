@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.charts

import kollections.List
import kollections.toIList
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class Chart<out D>(
    val title: String,
    val description: String,
    val labels: List<String>,
    val datasets: List<DataSet<D>>,
) {
    @Serializable
    data class DataSet<out D>(
        val name: String,
        val values: List<D>
    )

    fun <R> map(transform: (D) -> R): Chart<R> = Chart(
        title = title,
        description = description,
        labels = labels,
        datasets = datasets.map {
            DataSet(it.name, it.values.map(transform).toIList())
        }.toIList()
    )
}