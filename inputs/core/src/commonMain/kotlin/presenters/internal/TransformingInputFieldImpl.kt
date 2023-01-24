package presenters.internal

import kotlinx.serialization.KSerializer
import presenters.Formatter
import presenters.Label
import presenters.TransformingInputField
import presenters.internal.utils.DataTransformer
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class TransformingInputFieldImpl<I : Any, O : Any>(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    private val value: O?,
    override val hint: String,
    trnsfrmr: (I?) -> O?,
    override val serializer: KSerializer<O>,
    private val formatter: Formatter<O>?,
    override val isReadonly: Boolean,
    private val validator: ((O?) -> Unit)?,
) : TransformedDataField<I, O>(value), TransformingInputField<I, O> {
    override val cv = CompoundValidator(
        data, feedback, RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired), LambdaValidator(data, feedback, validator)
    )
    override val transformer: DataTransformer<I, O> = DataTransformer(formatter, trnsfrmr)
}