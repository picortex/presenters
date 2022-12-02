package presenters.forms

import presenters.actions.GenericAction

typealias FormActionsBuildingBlock<T> = FormActionsBuilder<T>.() -> GenericAction<T>