package presenters.collections

class ColumnsBuilder<D>(val columns: MutableList<Column<D>> = mutableListOf()) {
    fun selectable(name: String = "Select") {
        columns.add(Column.Select(name) as Column<D>)
    }

    fun column(name: String, accessor: (Row<D>) -> String) {
        columns += Column.Data(name, accessor)
    }

    fun action(name: String) {
        columns += Column.Action(name) as Column<D>
    }
}