package presenters.kost

import kost.Bill
import kost.BillHeader

@PublishedApi
internal fun Bill.renderToString(gap: Int = 4) = buildString {
    appendLine(header.renderToString())
    append(body.renderToString(gap))
}

private fun BillHeader.renderToString() = buildString {
    appendLine("BILL TO")
    appendLine("Supplier : ${supplier.name}")
    append(renderCommonInfoToString(supplier.address))
}