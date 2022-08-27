package presenters.forms.internal

import koncurrent.Executor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import logging.Logger
import presenters.forms.BaseForm
import presenters.forms.BaseFormViewModelConfig
import viewmodel.ViewModelConfig

class BaseFormViewModelConfigImpl<F : BaseForm<*, P>, P>(
    override val form: F,
    override val serializer: KSerializer<P>,
    override val executor: Executor,
    override val logger: Logger,
    override val codec: StringFormat
) : BaseFormViewModelConfig<F, P>, ViewModelConfig by ViewModelConfig(executor, logger)