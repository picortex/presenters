package presenters.fields.internal

import kollections.List
import kollections.iEmptyList
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.Live
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.fields.ListInputField
import presenters.fields.OutputData

@PublishedApi
internal class ListInputFieldImpl<E>(
    override val name: String,
    override val label: InputLabel,
    override val isReadonly: Boolean,
    override val isRequired: Boolean,
    override val maxItems: Int?,
    override val minItems: Int?,
    override val serializer: KSerializer<List<E>>
) : ListInputField<E> {
    override val data = mutableLiveOf<OutputData<List<E>>>(OutputData(iEmptyList()))
    override val feedback: Live<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    override fun add(item: E) {
        data.value = OutputData(((data.value.output ?: iEmptyList()) + item).toIList())
    }

    override fun remove(item: E) {
        data.value = OutputData((data.value.output ?: iEmptyList()).filter {
            it != item
        }.toIList())
    }

    override fun clear() {
        data.value = OutputData(iEmptyList())
    }
}