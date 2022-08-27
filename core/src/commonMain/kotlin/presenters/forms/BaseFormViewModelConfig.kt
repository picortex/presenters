@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.forms

import koncurrent.Executor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import logging.Logger
import presenters.forms.internal.BaseFormViewModelConfigImpl
import viewmodel.ViewModelConfig
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

@JsExport
interface BaseFormViewModelConfig<F : BaseForm<*, P>, P> : ViewModelConfig {
    val serializer: KSerializer<P>
    val form: F
    val codec: StringFormat

    companion object {

        @JvmField
        val DEFAULT_CODEC = Json

        @JvmName("create")
        @JsName("_ignore_invoke_1")
        operator fun <F : BaseForm<*, P>, P> invoke(
            form: F,
            serializer: KSerializer<P>,
            codec: StringFormat = DEFAULT_CODEC,
            executor: Executor = ViewModelConfig.DEFAULT_EXECUTOR,
            logger: Logger = ViewModelConfig.DEFAULT_LOGGER
        ): BaseFormViewModelConfig<F, P> = BaseFormViewModelConfigImpl(form, serializer, executor, logger, codec)

        @JvmSynthetic
        @JsName("_ignore_invoke_2")
        inline operator fun <F : BaseForm<F, P>, reified P> invoke(
            form: F,
            codec: StringFormat = DEFAULT_CODEC,
            executor: Executor = ViewModelConfig.DEFAULT_EXECUTOR,
            logger: Logger = ViewModelConfig.DEFAULT_LOGGER
        ): BaseFormViewModelConfig<F, P> = BaseFormViewModelConfigImpl(form, serializer(), executor, logger, codec)
    }
}