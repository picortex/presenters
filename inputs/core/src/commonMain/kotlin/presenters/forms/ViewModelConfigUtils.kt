@file:Suppress("WRONG_EXPORTED_DECLARATION")

package presenters.forms

import viewmodel.ViewModelConfig
import kotlinx.serialization.serializer
import presenters.forms.internal.FormConfigImpl
import presenters.forms.internal.FormConfigImpl.DEFAULT

inline fun <reified P> ViewModelConfig.toFormConfig(
    exitOnSubmitted: Boolean = DEFAULT.exitOnSubmitted
): FormConfig<P> = FormConfigImpl(
    serializer(), executor, logger, codec, cache, workManager, exitOnSubmitted
)