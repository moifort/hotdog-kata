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
import me.mottet.infra.Clock
import me.mottet.infra.Console
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InventoryFeature {
    @Mock lateinit var console: Console

    @Test
    fun `should print inventory`() {
        // Given
        val cashRegister = CashRegister(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                ProductCatalog(mapOf(HOT_DOG to Price(10.00), COCA_COLAS to Price(1.30))),
                Stock(sortedMapOf(HOT_DOG to 20, COCA_COLAS to 30), InventoryAlert(emptyMap(), Console(), Clock())),
                SalesBook(Clock()),
                ReceiptPrinter(Console()),
                InventoryPrinter(console))

        // When
        cashRegister.registerOrder(mapOf(HOT_DOG to 2, COCA_COLAS to 3))

        // Then
        assertThat(cashRegister.printInventory())

        // Then
        val inOrder = Mockito.inOrder(console)
        inOrder.verify(console).printLine("Inventory")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("Product | Quantity")
        inOrder.verify(console).printLine("Hot Dog | 18")
        inOrder.verify(console).printLine("Coca | 27")
    }
}

