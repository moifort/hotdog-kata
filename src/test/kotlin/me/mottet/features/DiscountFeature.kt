package me.mottet.features

import me.mottet.domain.discount.DiscountPercentOnProduct
import me.mottet.domain.discount.DiscountPriceOnProduct
import me.mottet.domain.inventory.InventoryAlert
import me.mottet.domain.inventory.InventoryPrinter
import me.mottet.domain.inventory.Stock
import me.mottet.domain.receipt.ReceiptPrinter
import me.mottet.domain.sale.CashRegister
import me.mottet.domain.sale.Price
import me.mottet.domain.sale.Product.COCA_COLAS
import me.mottet.domain.sale.Product.HOT_DOG
import me.mottet.domain.sale.ProductCatalog
import me.mottet.domain.sale.SalesBook
import me.mottet.domain.stand.Stand
import me.mottet.infra.Clock
import me.mottet.infra.Console
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class DiscountFeature {
    @Mock lateinit var console: Console
    @Mock lateinit var clock: Clock

    @Test
    fun `should apply discount on product`() {
        // Given
        `when`(clock.now()).thenReturn(LocalDateTime.of(2016, 4, 19, 10, 0))
        val cashRegister = CashRegister(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                ProductCatalog(mapOf(HOT_DOG to Price(10.0), COCA_COLAS to Price(1.30))),
                Stock(sortedMapOf(HOT_DOG to 20, COCA_COLAS to 30), InventoryAlert(emptyMap(), Console(), clock)),
                SalesBook(clock),
                ReceiptPrinter(console),
                InventoryPrinter(console))

        // When
        cashRegister.registerOrder(
                mapOf(HOT_DOG to 2, COCA_COLAS to 2),
                setOf(DiscountPercentOnProduct(HOT_DOG, 10.0), DiscountPriceOnProduct(COCA_COLAS, Price(1.00))))

        cashRegister.printReceipt()

        // Then
        val inOrder = Mockito.inOrder(console)
        inOrder.verify(console).printLine("Stand A")
        inOrder.verify(console).printLine("35 avenue Linkon - NYC")
        inOrder.verify(console).printLine("19-04-2016")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("Quantity | Description | Unit discountPrice | Total")
        inOrder.verify(console).printLine("2 | Hot Dog | 10.00 | 20.00")
        inOrder.verify(console).printLine("2 | Coca | 1.30 | 2.60")
        inOrder.verify(console).printLine("Discount")
        inOrder.verify(console).printLine(" | -10% Hot Dog | 1.00 | 2.00")
        inOrder.verify(console).printLine(" | -1.00 Coca | 1.00 | 2.00")
        inOrder.verify(console).printLine("TOTAL USD 18.60")
        inOrder.verify(console).printLine("Thank your for your visit!")
    }
}

