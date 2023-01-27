package presenters

import actions.Action1
import koncurrent.Later

typealias FormActionsBuildingBlock<P, R> = FormActionsBuilder<P, R>.() -> Action1<P, Later<R>>