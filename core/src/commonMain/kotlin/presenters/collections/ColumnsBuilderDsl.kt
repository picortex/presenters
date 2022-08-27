package presenters.collections

import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.toInteroperableList
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
fun <D> columnsOf(
    block: ColumnsBuilder<D>.() -> Unit
): List<Column<D>> {
    val builder = ColumnsBuilder<D>().apply(block)
    return builder.columns.toInteroperableList()
}