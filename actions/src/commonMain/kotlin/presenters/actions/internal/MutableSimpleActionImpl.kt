package presenters.actions.internal

import koncurrent.Later
import presenters.actions.MutableSimpleAction

@PublishedApi
internal class MutableSimpleActionImpl(
    override val name: String,
    override var handler: () -> Later<Any?>
) : MutableSimpleAction {
    override fun onInvoked(h: () -> Unit) {
        handler = { Later.resolve(h()) }
    }

    override fun invoke() = handler()
    override fun hashCode() = name.hashCode()
    override fun toString() = "Action($name)"
    override fun equals(other: Any?) = other is MutableSimpleAction && other.name == name
}