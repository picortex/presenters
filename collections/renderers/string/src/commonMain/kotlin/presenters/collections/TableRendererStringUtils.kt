package presenters.collections

private fun <D> Table<D>.text(row: Row<D>, col: Column<D>) = when (col) {
    is Column.Select -> (if (isRowSelectedOnCurrentPage(row.number)) "[x]" else "[ ]")
    is Column.Data -> col.accessor(row)
    is Column.Action -> actionsOf(row.item).joinToString(separator = "|") { it.name }
}


private fun <D> Table<D>.text(col: Column<D>) = when (col) {
    is Column.Data -> col.name
    is Column.Action -> col.name
    is Column.Select -> {
        val middle = when {
            isCurrentPageSelectedWholly() -> "x"
            isCurrentPageSelectedPartially() -> "-"
            else -> " "
        }
        "[$middle]"
    }
}

private fun <D> Table<D>.calculateColSizes(gap: Int): MutableMap<Column<D>, Int> {
    val colSizes = mutableMapOf<Column<D>, Int>()
    (paginator.page.value.data ?: Page()).items.forEach { row ->
        columns.get().forEach { col ->
            colSizes[col] = maxOf(colSizes[col] ?: 0, text(row, col).length + gap)
        }
    }
    return colSizes
}

private fun StringBuilder.appendRow(text: String, size: Int?) {
    append(text + " ".repeat(kotlin.math.max((size ?: 0) - text.length, 0)))
}

fun <D> Table<D>.renderToString(gap: Int = 4) = buildString {
    val colSizes = calculateColSizes(gap)
    columns.get().forEach { appendRow(text(it), colSizes[it]) }
    appendLine()
    appendLine()
    (paginator.page.value.data ?: Page()).items.forEach { row ->
        columns.get().forEach { col -> appendRow(text(row, col), colSizes[col]) }
        appendLine()
    }
}