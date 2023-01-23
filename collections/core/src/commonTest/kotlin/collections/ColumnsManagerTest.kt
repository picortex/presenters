package collections

import expect.expect
import live.WatchMode
import live.watch
import presenters.collections.columnsOf
import kotlin.test.Test

class ColumnsManagerTest {
    @Test
    fun should_be_able_to_build_columns() {
        val columns = columnsOf<Int> {
            selectable()
            column("Value") { it.item.toString() }
        }

        expect(columns.current.value).toBeOfSize(2)
        expect(columns.all()).toBeOfSize(2)
    }

    @Test
    fun should_current_live_columns_should_update_when_hiding_columns() {
        val columns = columnsOf<Int> {
            selectable()
            column("Value") { it.item.toString() }
        }
        var counts = 0
        columns.current.watch(mode = WatchMode.Casually) {
            counts++
        }
        expect(counts).toBe(0)
        columns.hide("Value")
        expect(columns.current.value).toBeOfSize(1)
        expect(counts).toBe(1)
    }

    @Test
    fun should_not_update_live_columns_when_hiding_unhidable_columns() {
        val columns = columnsOf<Int> {
            selectable()
            column("Value") { it.item.toString() }
        }
        var counts = 0
        columns.current.watch(mode = WatchMode.Casually) {
            counts++
        }
        expect(counts).toBe(0)
        columns.hide("count")
        expect(columns.current.value).toBeOfSize(2)
        expect(counts).toBe(0)
    }
}