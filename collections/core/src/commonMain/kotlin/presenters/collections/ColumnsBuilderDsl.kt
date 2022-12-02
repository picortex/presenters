package presenters.collections

import kollections.List
import kollections.toIList
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
fun <D> columnsOf(
    block: ColumnsBuilder<D>.() -> Unit
): List<Column<D>> {
    val builder = ColumnsBuilder<D>().apply(block)
    return builder.columns.toIList()
}