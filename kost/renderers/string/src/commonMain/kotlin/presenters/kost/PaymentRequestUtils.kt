package presenters.kost

import kost.Bill
import kost.Invoice
import kost.PaymentRequest

inline fun PaymentRequest.renderToString(gap: Int = 4) = when(this) {
    is Bill -> renderToString(gap)
    is Invoice -> renderToString(gap)
}