package me.mottet.domain.receipt

import me.mottet.domain.inventory.InventoryPrinter
import me.mottet.domain.sale.Product.COCA_COLAS
import me.mottet.domain.sale.Product.HOT_DOG
import me.mottet.infra.Console
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InventoryPrinterShould {
    @Mock lateinit var console: Console

    @Test
    fun `print`() {
        // Given
        val inventory = InventoryPrinter(console)

        // When
        inventory.print(mapOf(HOT_DOG to 30, COCA_COLAS to 20))

        // Then
        val inOrder = Mockito.inOrder(console)
        inOrder.verify(console).printLine("Inventory")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("Product | Quantity")
        inOrder.verify(console).printLine("Hot Dog | 30")
        inOrder.verify(console).printLine("Coca | 20")
    }
}

