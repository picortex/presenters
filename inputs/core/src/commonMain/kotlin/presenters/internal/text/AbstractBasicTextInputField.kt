package presenters.internal.text

import kotlinx.serialization.builtins.serializer
import live.MutableLive
import live.mutableLiveOf
import presenters.BasicTextInputField
import presenters.InputFieldState
import presenters.Label
import presenters.TextInputField
import presenters.internal.OutputData
import presenters.internal.PlainDataField
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.internal.utils.Typer
import presenters.internal.validators.CompoundValidator

abstract class AbstractBasicTextInputField(value: String?) : PlainDataField<String>(value), BasicTextInputField {
    final override val serializer = String.serializer()
    final override fun type(text: String) = Typer(data.value.output ?: "", setter).type(text)
}