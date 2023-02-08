@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import kollections.iMutableSetOf
import kollections.toIMutableSet
import presenters.collections.internal.ColumnsManagerImpl
import kotlin.jvm.JvmSynthetic

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
@JvmSynthetic
fun <D> columnsOf(
    columns: Collection<Column<D>> = setOf(),
    block: ColumnsBuilder<D>.() -> Unit
): ColumnsManager<D> = ColumnsManagerImpl(ColumnsBuilder(columns).apply(block))

@JvmSynthetic
inline fun <D> columnsOf(): ColumnsManager<D> = ColumnsManagerImpl(ColumnsBuilder())