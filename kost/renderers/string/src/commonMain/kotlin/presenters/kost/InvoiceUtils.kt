package presenters.kost

import kost.Invoice
import kost.InvoiceHeader

@PublishedApi
internal fun Invoice.renderToString(gap: Int = 4) = buildString {
    appendLine(header.renderToString())
    appendLine(body.renderToString(gap))
}

private fun InvoiceHeader.renderToString() = buildString {
    appendLine("INVOICE TO")
    appendLine("Customer : ${customer.name}")
    append(renderCommonInfoToString(customer.address))
}