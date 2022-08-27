package presenters.forms

import presenters.actions.GenericAction
import presenters.actions.SimpleAction
import presenters.forms.internal.BaseFormImpl

typealias FormActionsBuildingBlock<T> = FormActionsBuilder<T>.() -> GenericAction<T>

fun <F : Fields, P> BaseForm(
    heading: String,
    details: String,
    fields: F,
    initializer: FormActionsBuildingBlock<P>
): BaseForm<F, P> {
    val builtActions = FormActionsBuilder<P>().apply { initializer() }
    val cancel = builtActions.actions.firstOrNull {
        it.name.contentEquals("Cancel", ignoreCase = true)
    } ?: SimpleAction("Cancel") {}
    return BaseFormImpl(
        heading = heading,
        details = details,
        fields = fields,
        cancel = cancel,
        submit = builtActions.submitAction
    )
}