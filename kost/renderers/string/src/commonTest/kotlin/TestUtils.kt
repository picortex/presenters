import kash.Currency
import kost.Address
import kost.Bill
import kost.BillHeader
import kost.Body
import kost.Invoice
import kost.InvoiceHeader
import kost.LineItem
import kost.Subject
import kost.Tax
import krono.Now
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmOverloads

object TestUtils {
    const val UNSET = "<unset>"

    @JvmStatic
    fun makeAddress(): Address {
        return Address.Description("Test Address")
    }

    @JvmStatic
    @JvmOverloads
    fun makeSubject(
        name: String = "Test Subject",
        address: Address = makeAddress()
    ) = Subject(UNSET, name, address)

    fun makeTax(rate: Int = 18) = Tax(name = "Test Tax", rate)

    @JvmStatic
    @JvmOverloads
    fun makeBody(tax1: Tax = Tax.GENERIC_ZERO, tax2: Tax = makeTax(18)) = Body(
        LineItem(UNSET, "Computer Keyboard", quantity = 1, unitRate = 2_000_000, unitDiscount = 10_000, unit = "each"),
        LineItem(UNSET, "Call of Duty: Warzone", quantity = 2, unitRate = 10_000_000, unit = "man-days"),
        LineItem(UNSET, "HP Deskject Printer", quantity = 1, unitRate = 100_000, tax = tax1.copy()),
        LineItem(UNSET, "Samsung QLed 55 TV", quantity = 2, unitRate = 200_000, tax = tax2.copy()),
        LineItem(UNSET, "Samsung Galaxy Note 9", quantity = 3, unitRate = 300_000, tax = tax1.copy()),
        LineItem(UNSET, "Mitsubish RVR 2002", quantity = 4, unitRate = 400_000, unitDiscount = 1_000, tax = tax2.copy())
    )

    fun makeInvoice(address: Address = Address.Description("Test Address")) = Invoice(
        uid = "<unset>",
        header = InvoiceHeader(
            customer = makeSubject(address = address),
            currency = Currency.TZS,
            issueDate = Now()
        ),
        body = makeBody()
    )

    fun makeBill(address: Address = Address.Description("Test Address")) = Bill(
        uid = "<unset>",
        header = BillHeader(
            supplier = makeSubject(address = address),
            currency = Currency.TZS,
            issueDate = Now()
        ),
        body = makeBody()
    )
}
