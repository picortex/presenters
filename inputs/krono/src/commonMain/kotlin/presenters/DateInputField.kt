@file:JsExport

package presenters

import krono.LocalDate
import presenters.properties.Typeable
import kotlin.js.JsExport

interface DateInputField : TransformingInputField<String, LocalDate>, DateOutputField, Typeable