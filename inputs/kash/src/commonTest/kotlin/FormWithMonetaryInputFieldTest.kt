import expect.expect
import kash.Currency
import kash.Monetary
import koncurrent.Later
import kotlinx.serialization.Serializable
import presenters.Fields
import presenters.Form
import presenters.FormActionsBuildingBlock
import presenters.monetary
import presenters.name
import presenters.toFormConfig
import viewmodel.ViewModelConfig
import kotlin.test.Test

class FormWithMonetaryInputFieldTest {
    @Serializable
    data class TestParams(val name: String, val price: Monetary)

    class TestFields(currency: Currency?) : Fields() {
        val name = name()
        val price = monetary(
            name = TestParams::price
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
    fun should_submit_from_a_form_with_a_monetary_value() {
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