import expect.expect
import expect.toBe
import kash.Currency
import kash.TZS
import presenters.fields.MoneyInputField
import presenters.validation.Valid
import kotlin.test.Test

class MoneyInputFieldTest {
    @Test
    fun can_capture_money_without_problem() {
        val money = MoneyInputField("price")
        money.currency.selectItem(Currency.TZS)
        money.amount.set("200")

        val res = money.validate()
        expect(res).toBe<Valid>()

        expect(money.data.value).toBe(200.TZS)
    }

    @Test
    fun can_format_money_as_it_is_being_keyed_in() {
        val money = MoneyInputField("price")
        money.currency.selectItem(Currency.ZAR)
        money.amount.type("5000")
        money.amount.type("00")

        expect(money.data.value).toBe(200.TZS)
    }
}