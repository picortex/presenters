package forms

import expect.expect
import koncurrent.Later
import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.iListOf
import kotlinx.serialization.Serializable
import krono.LocalDate
import live.expect
import live.toHaveGoneThrough3
import presenters.fields.Option
import presenters.forms.Fields
import presenters.forms.Form
import presenters.forms.FormState
import presenters.forms.FormState.*
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
        val email by email(isRequired = true)
        val phone by phone(isRequired = true)
        val dob by date(isRequired = true)
        val color by selectSingle(items = Color.values().toList(), { Option(it.name) }, isRequired = true)
        val colors by selectMany(items = Color.values().toList(), { Option(it.name) }, isRequired = true)
    }

    @Serializable
    class AllParams(
        val name: String,
        val email: String,
        val phone: String,
        val dob: LocalDate,
        val color: Color,
        val colors: List<Color>
    )

    @Test
    fun should_be_able_to_submit_fields() {
        var params: AllParams? = null
        val form = Form<AllFields, AllParams>(
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
            name.value = "Andy"
            email.value = "andy@lamax.com"
            phone.value = "0752748674"
            dob.isoString = "2022-01-01"
            color.value = Color.Red
            colors.addSelectedItem(Color.Green)
            colors.addSelectedItem(Color.Blue)
        }
        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Submitted>()
        expect(params?.name).toBe("Andy")
        expect(params?.email).toBe("andy@lamax.com")
        expect(params?.phone).toBe("0752748674")
        expect(params?.dob).toBe(LocalDate(2022, 1, 1))
        expect(params?.color).toBe(Color.Red)
        expect(params?.colors).toBe(iListOf(Color.Green, Color.Blue))
    }
}