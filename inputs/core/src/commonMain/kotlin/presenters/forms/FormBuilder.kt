package presenters.forms

import actions.Action1I1R

typealias FormActionsBuildingBlock<P, R> = FormActionsBuilder<P, R>.() -> Action1I1R<P, R>