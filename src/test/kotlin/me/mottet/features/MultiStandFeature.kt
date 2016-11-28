package me.mottet.features

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
import me.mottet.domain.util.Clock
import me.mottet.domain.util.Console
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class MultiStandFeature {
    @Mock lateinit var console: Console
    @Mock lateinit var clock: Clock


    @Test
    fun `should manage several stand`() {
        // Given
        Mockito.`when`(clock.now()).thenReturn(LocalDateTime.of(2016, 4, 19, 10, 0))
        val stock = Stock(sortedMapOf(HOT_DOG to 20, COCA_COLAS to 30), InventoryAlert(emptyMap(), Console(), clock))
        val saleRepository = SalesBook(clock)
        val receipt = ReceiptPrinter(console)
        val inventory = InventoryPrinter(console)
        val cashRegisterStandA = CashRegister(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                ProductCatalog(mapOf(HOT_DOG to Price(10.00), COCA_COLAS to Price(1.30))),
                stock,
                saleRepository,
                receipt,
                inventory)

        // When
        cashRegisterStandA.registerOrder(mapOf(HOT_DOG to 1, COCA_COLAS to 2))

        cashRegisterStandA.printReceipt()

        // Then
        val inOrder = Mockito.inOrder(console)
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



        val cashRegisterStandB = CashRegister(
                Stand("Stand B", "48 Baker street - NYC"),
                ProductCatalog(mapOf(HOT_DOG to Price(8.00), COCA_COLAS to Price(2.0))),
                stock,
                saleRepository,
                receipt,
                inventory)

        // When
        cashRegisterStandB.registerOrder(mapOf(HOT_DOG to 1, COCA_COLAS to 2))

        cashRegisterStandB.printReceipt()

        // Then
        inOrder.verify(console).printLine("Stand B")
        inOrder.verify(console).printLine("48 Baker street - NYC")
        inOrder.verify(console).printLine("19-04-2016")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("Quantity | Description | Unit discountPrice | Total")
        inOrder.verify(console).printLine("1 | Hot Dog | 8.00 | 8.00")
        inOrder.verify(console).printLine("2 | Coca | 2.00 | 4.00")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("TOTAL USD 12.00")
        inOrder.verify(console).printLine("Thank your for your visit!")
    }
}

