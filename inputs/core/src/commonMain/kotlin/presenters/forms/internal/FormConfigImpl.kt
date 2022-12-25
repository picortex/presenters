package presenters.forms.internal

import cache.Cache
import koncurrent.Executor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.serializer
import krest.WorkManager
import logging.Logger
import presenters.forms.FormConfig
import viewmodel.VIEW_MODEL_CONFIG_DEFAULT
import viewmodel.ViewModelConfig

open class FormConfigImpl<P>(
    override val serializer: KSerializer<P>,
    override val executor: Executor,
    override val logger: Logger,
    override val codec: StringFormat,
    override val cache: Cache,
    override val workManager: WorkManager,
    override val exitOnSubmitted: Boolean
) : FormConfig<P>, ViewModelConfig by ViewModelConfig(executor, logger, cache, codec, workManager) {
    companion object DEFAULT : FormConfigImpl<Unit>(
        serializer = Unit.serializer(),
        executor = VIEW_MODEL_CONFIG_DEFAULT.executor,
        logger = VIEW_MODEL_CONFIG_DEFAULT.logger,
        codec = VIEW_MODEL_CONFIG_DEFAULT.codec,
        cache = VIEW_MODEL_CONFIG_DEFAULT.cache,
        workManager = VIEW_MODEL_CONFIG_DEFAULT.workManager,
        exitOnSubmitted = true,
    )
}