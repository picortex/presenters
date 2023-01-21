package presenters.internal.utils

import presenters.properties.Settable
import presenters.properties.Typeable

class Typer(
    private val old: String?,
    private val setter: Settable<String>
) : Typeable {
    override fun type(text: String) {
        for (i in 0..text.lastIndex) {
            setter.set((old ?: "") + text.substring(0..i))
        }
    }
}