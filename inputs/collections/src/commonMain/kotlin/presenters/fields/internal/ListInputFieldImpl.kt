package presenters.fields.internal

import kollections.List
import kollections.iEmptyList
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.Live
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.Label
import presenters.fields.ListInputField

@PublishedApi
internal class ListInputFieldImpl<E>(
    override val name: String,
    override val label: Label,
    defaultValue: Collection<E>,
    override val isReadonly: Boolean,
    override val isRequired: Boolean,
    override val maxItems: Int?,
    override val minItems: Int?,
    override val serializer: KSerializer<List<E>>
) : ListInputField<E> {
    override val data = mutableLiveOf(OutputList(defaultValue.toIList()))
    override val feedback: Live<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    override fun add(item: E) {
        data.value = OutputList((data.value.output + item).toIList())
    }

    override fun remove(item: E) {
        data.value = OutputList(data.value.output.filter {
            it != item
        }.toIList())
    }

    override fun update(item: E, updater: () -> E) {
        val list = data.value.output.toMutableList()
        val idx = list.indexOf(item)
        list.remove(item)
        list.add(idx, updater())
        data.value = OutputList(list.toIList())
    }

    override fun clear() {
        data.value = OutputList(iEmptyList())
    }
}