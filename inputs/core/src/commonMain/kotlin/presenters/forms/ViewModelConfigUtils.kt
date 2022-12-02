@file:Suppress("WRONG_EXPORTED_DECLARATION")

package presenters.forms

import viewmodel.ViewModelConfig
import kotlinx.serialization.serializer
import presenters.forms.internal.FormConfigImpl

inline fun <reified P> ViewModelConfig.toFormConfig(
    exitOnSubmitted: Boolean = FormConfig.DEFAULT_EXIT_ON_SUBMITTED
): FormConfig<P> = FormConfigImpl(
    serializer(), executor, logger, codec, exitOnSubmitted
)