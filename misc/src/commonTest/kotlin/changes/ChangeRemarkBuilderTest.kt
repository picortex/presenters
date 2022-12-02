package changes

import expect.expect
import expect.toBe
import kash.TZS
import kash.USD
import presenters.changes.ChangeRemark
import presenters.changes.changeRemarkOf
import presenters.numerics.pct
import kotlin.test.Ignore
import kotlin.test.Test

class ChangeRemarkBuilderTest {
    @Test
    fun should_get_change_remark_of_money() {
        val change = changeRemarkOf(
            previous = 100.TZS,
            current = 120.TZS
        ) as ChangeRemark.Increase
        expect(change.pct).toBe(20.pct)
        expect(change.value).toBe(20.TZS)
    }

    @Test
    fun should_return_an_indeterminate_value_when_comparing_different_currencies() {
        val change = changeRemarkOf(
            previous = 100.TZS,
            current = 300.USD
        )
        expect(change).toBe<ChangeRemark.Indeterminate>()
    }

    @Test
    fun should_give_a_change_remark_of_100pct_when_previous_value_is_zero() {
        val change = changeRemarkOf(
            previous = 0.USD,
            current = 300.USD
        ) as ChangeRemark.Increase

        expect(change.value).toBe(300.USD)
        expect(change.pct).toBe(100.pct)
    }

    @Test
    @Ignore // TODO think of another way, there is no negative money
    fun should_give_a_change_remark_of_100pct_when_previous_value_is_zero_and_current_value_is_negative() {
        val change = changeRemarkOf(
            previous = 0.USD,
            current = (-300).USD
        ) as ChangeRemark.Decrease

        expect(change.value).toBe(300.USD)
        expect(change.pct).toBe(100.pct)
    }
}