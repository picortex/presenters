package presenters.forms

import actions.GenericAction

typealias FormActionsBuildingBlock<P, R> = FormActionsBuilder<P, R>.() -> GenericAction<P, R>