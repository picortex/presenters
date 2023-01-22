@file:JsExport

package presenters

import krono.LocalDate
import presenters.properties.Bounded
import presenters.properties.Patterned
import kotlin.js.JsExport

interface DateOutputField : SerializableLiveFormattedData<String, LocalDate>, Bounded<LocalDate>, Patterned