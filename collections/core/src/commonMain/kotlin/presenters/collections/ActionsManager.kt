@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import actions.Action0
import kollections.List
import live.Live
import kotlin.js.JsExport

interface ActionsManager<T> {
    val current: Live<List<Action0<Unit>>>

    fun get(): List<Action0<Unit>>

    fun add(name: String, handler: () -> Unit): ActionsManager<T>

    fun addSingle(name: String, handler: (T) -> Unit): ActionsManager<T>

    fun addMulti(name: String, handler: (List<T>) -> Unit): ActionsManager<T>

    fun remove(key: String): ActionsManager<T>

    fun of(item: T): List<Action0<Unit>>
}