package presenters.collections.internal

import kollections.List
import presenters.collections.ActionsManager
import presenters.collections.CollectionActionsBuilder
import presenters.collections.SelectionManager

@PublishedApi
internal class ActionsManagerImpl<T>(
    private val selector: SelectionManager<T>,
    private val builder: CollectionActionsBuilder<T>
) : ActionsManager<T> {
    override val current = selector.selected.map {
        builder.buildActions(it)
    }

    override fun get() = current.value

    override fun add(name: String, handler: () -> Unit): ActionsManager<T> {
        builder.primary { on(name, handler = handler) }
        return this
    }

    override fun addSingle(name: String, handler: (T) -> Unit): ActionsManager<T> {
        builder.single { on(name) { handler(it) } }
        return this
    }

    override fun addMulti(name: String, handler: (List<T>) -> Unit): ActionsManager<T> {
        builder.multi { on(name) { handler(it) } }
        return this
    }

    override fun remove(key: String): ActionsManager<T> {
        builder.filters.add(key.lowercase())
        return this
    }

    override fun of(item: T) = builder.buildSingleSelectActions(item)
}