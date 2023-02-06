import expect.expect
import expect.toBe
import kash.Currency
import kash.TZS
import presenters.MoneyInputField
import presenters.validation.Valid
import kotlin.test.Test

class MoneyInputFieldFormatterTest {

    @Test
    fun should_be_able_to_format_strings() {
        val money = MoneyInputField("price")
        money.currency.selectItem(Currency.TZS)
        money.setAmount("200")

        val res = money.validate()
        expect(res).toBe<Valid>()

        expect(money.data.value.output).toBe(200.TZS)
    }

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
        with(money.data.value) {
            expect(input).toBe("R 500")
            expect(formatted).toBe("R 500")
            expect(output?.amountAsDouble).toBe(500.0)
        }

        money.amount.type("0")
        with(money.data.value) {
            expect(input).toBe("R 5,000")
            expect(formatted).toBe("R 5,000")
            expect(output?.amountAsDouble).toBe(5000.0)
        }

        money.amount.type("0")
        with(money.data.value) {
            expect(input).toBe("R 50,000")
            expect(formatted).toBe("R 50,000")
            expect(output?.amountAsDouble).toBe(50000.0)
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
        with(money.data.value) {
            expect(input).toBe("R 500")
            expect(formatted).toBe("R 500")
            expect(output?.amountAsDouble).toBe(500.0)
        }

        money.amount.type(",")
        with(money.data.value) {
            expect(input).toBe("R 500")
            expect(formatted).toBe("R 500")
            expect(output?.amountAsDouble).toBe(500.0)
        }

        money.amount.type("0")
        with(money.data.value) {
            expect(input).toBe("R 5,000")
            expect(formatted).toBe("R 5,000")
            expect(output?.amountAsDouble).toBe(5_000.0)
        }

        money.amount.set("12500")
        with(money.data.value) {
            expect(input).toBe("R 12,500")
            expect(formatted).toBe("R 12,500")
            expect(output?.amountAsDouble).toBe(12_500.0)
        }
    }
}