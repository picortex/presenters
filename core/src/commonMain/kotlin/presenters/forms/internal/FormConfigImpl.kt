package presenters.forms.internal

import koncurrent.Executor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import logging.Logger
import presenters.forms.FormConfig
import viewmodel.ViewModelConfig

class FormConfigImpl<P>(
    override val serializer: KSerializer<P>,
    override val executor: Executor,
    override val logger: Logger,
    override val codec: StringFormat,
    override val exitOnSubmitted: Boolean
) : FormConfig<P>, ViewModelConfig by ViewModelConfig()