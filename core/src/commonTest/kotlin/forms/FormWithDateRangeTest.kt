package forms

import expect.expect
import koncurrent.Later
import koncurrent.later.catch
import koncurrent.later.then
//import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import live.expect
import live.toHaveGoneThrough1
import live.toHaveGoneThrough2
import live.toHaveGoneThrough3
import presenters.forms.*
import presenters.forms.FormState.*
import presenters.forms.fields.dateRange
import presenters.forms.fields.email
import presenters.forms.fields.number
import presenters.forms.fields.text
import viewmodel.ScopeConfig
import viewmodel.ViewModel
import viewmodel.ViewModelConfig
import kotlin.test.Test

class FormWithDateRangeTest {

    class PersonForm(
        config: FormConfig<Map<String, String>>,
        builder: FormActionsBuildingBlock<Map<String, String>>
    ) : Form<TestFormFields, Map<String, String>>(
        heading = "Person Form",
        details = "Add this form to fill a person",
        fields = TestFormFields(),
        config, builder
    )

    class TestFormFields : Fields() {
        val name by text(isRequired = true)
        val range by dateRange(isRequired = true)
    }

    @Test
    fun person_form_should_be_able_to_recover_after_failure() = runTest {
        val form = PersonForm(ScopeConfig(Unit).toFormConfig()) {
            onSubmit {
                println(it.entries.joinToString { entry -> "${entry.key}=${entry.value}" })
                Later.resolve(Unit)
            }
        }

        form.fields.apply {
            name.value = "Anderson"
            range.startIsoString = "andy@lamax"
        }
        form.submit()
        val (_, s1) = expect(form.ui).toHaveGoneThrough2<Validating, Failure>()
        expect(s1.message).toBe("You have 1 invalid input")
        form.ui.history.clear()

        form.fields.apply {
            name.value = "Anderson"
            range.startIsoString = "2021-01-10"
            range.endIsoString = "2021-01-11"
        }
        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Submitted>()
    }
}