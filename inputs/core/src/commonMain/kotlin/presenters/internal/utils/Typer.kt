package presenters.internal.utils

import presenters.fields.properties.Settable
import presenters.fields.properties.Typeable

internal class Typer(
    private val old: String?,
    private val setter: Settable<String>
) : Typeable {
    override fun type(text: String) {
        for (i in 0..text.lastIndex) {
            setter.set((old ?: "") + text.substring(0..i))
        }
    }
}