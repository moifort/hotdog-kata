package me.mottet.domain.receipt

import me.mottet.domain.sale.Item
import me.mottet.domain.sale.Order
import me.mottet.domain.sale.Price
import me.mottet.domain.sale.Product.COCA_COLAS
import me.mottet.domain.sale.Product.HOT_DOG
import me.mottet.domain.sale.Sale
import me.mottet.domain.stand.Stand
import me.mottet.infra.Console
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class ReceiptShould {
    @Mock lateinit var console: Console

    @Test
    fun `print`() {
        // Given
        val receipt = ReceiptPrinter(console)

        // When
        receipt.print(Sale(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                Order(listOf(
                        Item(HOT_DOG, 1, Price(10.0)),
                        Item(COCA_COLAS, 2, Price(1.3))),
                        LocalDateTime.of(2016, 4, 19, 11, 0))))

        val inOrder = inOrder(console)
        inOrder.verify(console).printLine("Stand A")
        inOrder.verify(console).printLine("35 avenue Linkon - NYC")
        inOrder.verify(console).printLine("19-04-2016")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("Quantity | Description | Unit discountPrice | Total")
        inOrder.verify(console).printLine("1 | Hot Dog | 10.00 | 10.00")
        inOrder.verify(console).printLine("2 | Coca | 1.30 | 2.60")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("TOTAL USD 12.60")
        inOrder.verify(console).printLine("Thank your for your visit!")
    }
}

