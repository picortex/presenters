import expect.expect
import kase.Validating
import kase.Success
import kase.Submitting
import kase.Failure
import koncurrent.Later
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import krono.LocalDate
import live.expect
import live.toHaveGoneThrough2
import live.toHaveGoneThrough3
import presenters.Fields
import presenters.Form
import presenters.FormActionsBuildingBlock
import presenters.FormConfig
import presenters.Range
import presenters.dateRange
import presenters.text
import presenters.toFormConfig
import viewmodel.ScopeConfig
import kotlin.test.Ignore
import kotlin.test.Test

class FormWithDateRangeTest {

    @Serializable
    data class Params(
        val name: String,
        val range: Range<LocalDate>
    )

    class PersonForm(
        config: FormConfig<Params>,
        builder: FormActionsBuildingBlock<Params, Any?>
    ) : Form<TestFormFields, Params, Any?>(
        heading = "Person Form",
        details = "Add this form to fill a person",
        fields = TestFormFields(),
        config, builder
    )

    class TestFormFields : Fields() {
        val name = text(name = Params::name, isRequired = true)
        val range = dateRange(name = Params::range, isRequired = true)
    }

    @Test
    fun person_form_should_be_able_to_recover_after_failure() = runTest {
        val form = PersonForm(ScopeConfig(Unit).toFormConfig()) {
            onSubmit {
                println(it)
                Later(Unit)
            }
        }

        form.fields.apply {
            name.type("Anderson")
            range.setStart("andy@lamax")
        }
        form.submit()
        val (_, s1) = expect(form.ui).toHaveGoneThrough2<Validating, Failure<*>>()
        expect(s1.message).toBe("You have 1 invalid input")
        form.ui.history.clear()

        form.fields.apply {
            name.type("Anderson")
            range.setStart("2021-01-10")
            range.setEnd("2021-01-11")
        }
        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<*>>()
    }
}