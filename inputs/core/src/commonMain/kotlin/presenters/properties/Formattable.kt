package presenters.properties

import presenters.Formatter

interface Formattable<in I, O> {
    val formatter: Formatter<O>?
    val transformer: (I?) -> O?
}