@file:Suppress("WRONG_EXPORTED_DECLARATION")

package presenters

import viewmodel.ViewModelConfig
import kotlinx.serialization.serializer
import presenters.internal.forms.FormConfigImpl
import presenters.internal.forms.FormConfigImpl.DEFAULT

inline fun <reified P> ViewModelConfig.toFormConfig(
    exitOnSubmitted: Boolean = DEFAULT.exitOnSubmitted
): FormConfig<P> = FormConfigImpl(
    Unit, serializer(), executor, logger, codec, cache, workManager, exitOnSubmitted
)