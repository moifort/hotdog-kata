package me.mottet.domain.receipt

import me.mottet.domain.inventory.InventoryPrinter
import me.mottet.domain.sale.Product.COCA_COLAS
import me.mottet.domain.sale.Product.HOT_DOG
import me.mottet.domain.util.Clock
import me.mottet.domain.util.Console
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class InventoryPrinterShould {
    @Mock lateinit var console: Console
    @Mock lateinit var clock: Clock

    @Test
    fun `print`() {
        // Given
        `when`(clock.now()).thenReturn(LocalDateTime.of(2016,4, 19, 11, 0))
        val inventory = InventoryPrinter(console, clock)

        // When
        inventory.print(mapOf(HOT_DOG to 30, COCA_COLAS to 20))

        // Then
        val inOrder = Mockito.inOrder(console)
        inOrder.verify(console).printLine("Inventory")
        inOrder.verify(console).printLine("19-04-2016")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("Product | Quantity")
        inOrder.verify(console).printLine("Hot Dog | 30")
        inOrder.verify(console).printLine("Coca | 20")
    }
}

