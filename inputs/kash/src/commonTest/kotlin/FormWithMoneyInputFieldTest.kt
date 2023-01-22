import expect.expect
import kash.Currency
import kash.Money
import koncurrent.Later
import kotlinx.serialization.Serializable
import presenters.Fields
import presenters.Form
import presenters.FormActionsBuildingBlock
import presenters.money
import presenters.name
import presenters.toFormConfig
import viewmodel.ViewModelConfig
import kotlin.test.Test

class FormWithMoneyInputFieldTest {
    @Serializable
    data class TestParams(val name: String, val price: Money)
    class TestFields(currency: Currency?) : Fields() {
        val name = name()
        val price = money(
            name = TestParams::price,
            currency = currency
        )
    }

    class TestForm(
        curreny: Currency?,
        init: FormActionsBuildingBlock<TestParams, TestParams>
    ) : Form<TestFields, TestParams, TestParams>(
        heading = "Test Form",
        details = "This is a test form",
        fields = TestFields(curreny),
        config = ViewModelConfig().toFormConfig(),
        initializer = init
    )

    @Test
    fun should_submit_from_a_form_with_a_null_currency_value() {
        var params: TestParams? = null
        val form = TestForm(null) {
            onCancel { println("Cancelled") }
            onSubmit {
                params = it
                Later(it)
            }
        }

        with(form.fields) {
            name.type("Lamax")
            price.setCurrency("TZS")
            price.setAmount(200)
        }
        form.submit()

        expect(params?.price?.amountAsDouble).toBe(200.0)
    }

    @Test
    fun should_submit_from_a_form_with_a_non_null_currency_value() {
        var params: TestParams? = null
        val form = TestForm(Currency.TZS) {
            onCancel { println("Cancelled") }
            onSubmit {
                params = it
                Later(it)
            }
        }

        with(form.fields) {
            name.type("Lamax")
            price.setAmount(200)
        }
        form.submit()

        expect(params?.price?.amountAsDouble).toBe(200.0)
    }
}