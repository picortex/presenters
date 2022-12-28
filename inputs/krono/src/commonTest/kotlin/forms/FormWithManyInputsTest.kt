package forms

import expect.expect
import kase.Success
import kollections.toIList
import koncurrent.Later
import kase.Validating
import kase.Submitting
import kotlinx.serialization.Serializable
import krono.LocalDate
import live.expect
import live.toHaveGoneThrough3
import presenters.fields.Option
import presenters.forms.Fields
import presenters.forms.Form
import presenters.forms.fields.*
import presenters.forms.toFormConfig
import viewmodel.ViewModelConfig
import kotlin.test.Test

class FormWithManyInputsTest {

    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    class AllFields : Fields() {
        val name by name(isRequired = true)
        val dob by date(isRequired = true)
        val color by selectSingle(items = Color.values().toIList(), { Option(it.name) }, isRequired = true)
    }

    @Serializable
    class AllParams(
        val name: String,
        val dob: LocalDate,
        val color: Color
    )

    @Test
    fun should_be_able_to_submit_fields() {
        var params: AllParams? = null
        val form = Form<AllFields, AllParams, Any?>(
            heading = "The god form",
            details = "A form to test things out",
            fields = AllFields(),
            config = ViewModelConfig().toFormConfig()
        ) {
            onSubmit {
                params = it
                Later.resolve(it)
            }
        }
        form.fields.apply {
            name.type("Andy")
            dob.set("2022-01-01")
            color.selectItem(Color.Red)
        }
        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<*>>()
        expect(params?.name).toBe("Andy")
        expect(params?.dob).toBe(LocalDate(2022, 1, 1))
        expect(params?.color).toBe(Color.Red)
    }
}