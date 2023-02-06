package presenters.kost

import kost.Body
import presenters.collections.renderToString
import presenters.collections.simpleTableOf

internal fun Body.renderToString(gap: Int = 4) = buildString {
    appendLine("Items:")
    appendLine()
    append(renderItemsToString(gap))
    appendLine()
    appendLine("Summary:")
    appendLine()
    append(renderSummaryToString())
}

private fun Body.renderItemsToString(gap: Int = 4) = simpleTableOf(items) {
    column("Details") { it.item.details }
    column("Quantity") { it.item.quantity.toString() }
    column("Unit rate") { "${it.item.unitRate}/${it.item.unit}" }
    if (!items.all { it.unitDiscount <= 0L }) column("Unit discount") {
        it.item.unitDiscount.toString()
    }
    if (!items.all { it.taxAmount <= 0L }) column("Tax") {
        it.item.taxAmount.toString()
    }
    column("Cost") { it.item.costAfterTax.toString() }
}.renderToString(gap)

private fun Body.renderSummaryToString() = buildString {
    if (unitDiscount <= 0L && taxAmount <= 0L) {
        appendLine("Total               : $costAfterTax")
    }

    if (unitDiscount > 0L) {
        appendLine("Cost Before Discount: $costBeforeDiscount")
        appendLine("Discount            : $unitDiscount")
        appendLine("Cost After Discount : $costAfterDiscount")
    }

    if (unitDiscount <= 0L && taxAmount > 0L) {
        appendLine("Cost Before Tax     : $costBeforeTax")
    }

    if (taxAmount > 0L) {
        appendLine("Tax                 : $taxAmount")
        appendLine("Cost After Tax      : $costAfterTax")
    }
}