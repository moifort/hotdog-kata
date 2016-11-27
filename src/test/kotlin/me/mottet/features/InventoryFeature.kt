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
import me.mottet.domain.sale.SaleRepository
import me.mottet.domain.stand.Stand
import me.mottet.domain.util.Clock
import me.mottet.domain.util.Console
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class InventoryFeature {
    @Mock lateinit var console: Console
    @Mock lateinit var clock: Clock

    @Test
    fun `should print inventory`() {
        // Given
        Mockito.`when`(clock.now()).thenReturn(LocalDateTime.of(2016, 4, 19, 10, 0))
        val cashRegister = CashRegister(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                ProductCatalog(mapOf(HOT_DOG to Price(10.00), COCA_COLAS to Price(1.30))),
                Stock(sortedMapOf(HOT_DOG to 20, COCA_COLAS to 30), InventoryAlert(emptyMap(), Console(), clock)),
                SaleRepository(Clock()),
                ReceiptPrinter(Console()),
                InventoryPrinter(console, clock))

        // When
        cashRegister.registerOrder(mapOf(HOT_DOG to 2, COCA_COLAS to 3))

        // Then
        assertThat(cashRegister.printInventory())

        // Then
        val inOrder = Mockito.inOrder(console)
        inOrder.verify(console).printLine("Inventory")
        inOrder.verify(console).printLine("19-04-2016")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("Product | Quantity")
        inOrder.verify(console).printLine("Hot Dog | 18")
        inOrder.verify(console).printLine("Coca | 27")
    }
}

