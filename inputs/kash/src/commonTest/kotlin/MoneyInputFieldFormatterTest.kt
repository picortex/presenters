import expect.expect
import expect.toBe
import kash.Currency
import kash.TZS
import presenters.MoneyInputField
import presenters.validation.Valid
import kotlin.test.Test

class MoneyInputFieldFormatterTest {
    @Test
    fun can_capture_money_without_problem() {
        val money = MoneyInputField("price")
        money.currency.selectItem(Currency.TZS)
        money.amount.set("200")

        val res = money.validate()
        expect(res).toBe<Valid>()

        expect(money.data.value.output).toBe(200.TZS)
    }

    @Test
    fun can_format_money_as_it_is_being_keyed_in() {
        val money = MoneyInputField("price")
        val watcher1 = money.data.watch {
            println("Money: ${it.formatted}")
        }
        money.currency.selectItem(Currency.ZAR)
        val watcher2 = money.amount.data.watch { println("Amount ${it.formatted}") }
        money.amount.type("500")
        with(money.amount.data.value) {
            expect(input).toBe("500")
            expect(formatted).toBe("500")
            expect(output).toBe(500.0)
        }

        money.amount.type("0")
        with(money.amount.data.value) {
            expect(input).toBe("5000")
            expect(formatted).toBe("5,000")
            expect(output).toBe(5000.0)
        }

        money.amount.type("0")
        with(money.amount.data.value) {
            expect(input).toBe("50000")
            expect(formatted).toBe("50,000")
            expect(output).toBe(50000.0)
        }

        watcher1.stop()
        watcher2.stop()
        money.data.stopAll()
        money.amount.data.stopAll()
    }

    @Test
    fun can_be_kind_to_manual_formatters() {
        val money = MoneyInputField("price")
        money.currency.selectItem(Currency.ZAR)
        money.amount.type("500")
        with(money.amount.data.value) {
            expect(input).toBe("500")
            expect(formatted).toBe("500")
            expect(output).toBe(500.0)
        }

        money.amount.type(",")
        with(money.amount.data.value) {
            expect(input).toBe("500,")
            expect(formatted).toBe("500")
            expect(output).toBe(500.0)
        }

        money.amount.type("0")
        with(money.amount.data.value) {
            expect(input).toBe("500,0")
            expect(formatted).toBe("5,000")
            expect(output).toBe(5_000.0)
        }

        money.amount.set("12500")
        with(money.amount.data.value) {
            expect(input).toBe("12500")
            expect(formatted).toBe("12,500")
            expect(output).toBe(12_500.0)
        }
    }
}