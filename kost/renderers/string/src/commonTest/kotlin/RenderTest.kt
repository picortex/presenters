import presenters.kost.renderToString
import kotlin.test.Test

class RenderTest {
    @Test
    fun should_print_invoices_to_console_easily() {
        val invoice = TestUtils.makeInvoice()
        println(invoice.renderToString())
    }

    @Test
    fun should_print_bills_to_console_easily() {
        val bill = TestUtils.makeBill()
        println(bill.renderToString())
    }
}