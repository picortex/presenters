package presenters.collections

import kotlin.math.max

private fun <D> Table<D>.calculateColSizes(gap: Int): MutableMap<Column<D>, Int> {
    val colSizes = mutableMapOf<Column<D>, Int>()
    (paginator.page.value.data ?: Page()).items.forEach { row ->
        columns.forEach { col ->
            val text = when (col) {
                is Column.Select -> (if (isRowSelectedOnCurrentPage(row.number)) "[x]" else "[ ]")
                is Column.Data -> col.accessor(row)
                is Column.Action -> actionsOf(row.item).joinToString(separator = "|") { it.name }
            }
            colSizes[col] = maxOf(colSizes[col] ?: 0, text.length + gap)
        }
    }
    return colSizes
}

fun <D> Table<D>.tabulateToString(gap: Int = 4) = buildString {
    appendLine()
    appendLine()
    val colSizes = calculateColSizes(gap)
    columns.forEach {
        if (it is Column.Select) {
            val middle = when {
                isCurrentPageSelectedWholly() -> "x"
                isCurrentPageSelectedPartially() -> "-"
                else -> " "
            }
            append("[$middle]\t")
        } else {
            val size = max(colSizes[it] ?: 0, it.name.length + gap)
            colSizes[it] = size
            append(it.name + " ".repeat(size - it.name.length))
        }
        if (it.name == "Name") {
            append("\t\t")
        }
    }
    appendLine()
    appendLine()
    (paginator.page.value.data ?: Page()).items.forEach { row ->
        columns.forEach { col ->
            val text = when (col) {
                is Column.Select -> (if (isRowSelectedOnCurrentPage(row.number)) "[x]" else "[ ]")
                is Column.Data -> col.accessor(row)
                is Column.Action -> actionsOf(row.item).joinToString(separator = "|") { it.name }
            }
            val size = colSizes[col] ?: 0
            append(text + " ".repeat(size - text.length))
        }
        appendLine()
    }
}