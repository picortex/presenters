package collections

import expect.expect
import presenters.collections.CollectionPaginator
import presenters.collections.SelectionManager
import presenters.collections.actionsOf
import presenters.collections.renderToConsole
import presenters.collections.tableOf
import kotlin.test.Test
import kotlin.test.fail

class TableColumnTransformersTest {
    @Test
    fun should_be_able_to_filter_out_columns_after_the_table_has_been_created() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val action = actionsOf(selector) {}
        var table = tableOf(paginator, selector, action, Person.columns())
        table.loadFirstPage()
        table.renderToConsole()
        expect(table.columns.get()).toBeOfSize(4)
        table = table.columns.remove("name").finish()
        table.renderToConsole()
        expect(table.columns.get()).toBeOfSize(3)
    }
}