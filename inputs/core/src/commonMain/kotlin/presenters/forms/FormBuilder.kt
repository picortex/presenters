package presenters.forms

import actions.Action1

typealias FormActionsBuildingBlock<P, R> = FormActionsBuilder<P, R>.() -> Action1<P, R>