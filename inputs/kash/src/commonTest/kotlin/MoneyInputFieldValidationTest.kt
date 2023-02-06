import expect.expect
import expect.toBe
import kash.Currency
import kash.TZS
import presenters.MoneyInputField
import presenters.validation.Valid
import kotlin.test.Test

class MoneyInputFieldValidationTest {
    @Test
    fun can_validate_capture_money_without_problem() {
        val money = MoneyInputField("price")
        money.currency.selectItem(Currency.TZS)
        money.amount.set("200")

        val res = money.validate()
        expect(res).toBe<Valid>()

        expect(money.data.value.output).toBe(200.TZS)
    }
}