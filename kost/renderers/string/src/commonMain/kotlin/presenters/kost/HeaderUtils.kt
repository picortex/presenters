package presenters.kost

import kost.Address
import kost.Header

internal fun Header.renderCommonInfoToString(address: Address) = buildString {
    appendLine("Address  : $address")
    appendLine("Currency : ${currency.details} ($currency)")
    appendLine("Created  : $createdOn")
    appendLine("Due      : $dueOn")
    appendLine("Vendor   : ${vendor.name}")
}