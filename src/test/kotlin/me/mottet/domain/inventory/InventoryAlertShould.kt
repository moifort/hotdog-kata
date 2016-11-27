package me.mottet.domain.receipt

import me.mottet.domain.inventory.InventoryAlert
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
class InventoryAlertShould {
    @Mock lateinit var console: Console
    @Mock lateinit var clock: Clock

    @Test
    fun `notify when quantity is out of stock`() {
        // Given
        `when`(clock.now()).thenReturn(LocalDateTime.of(2016,4, 19, 11, 0))
        val inventoryAlert = InventoryAlert(mapOf(HOT_DOG to 5), console, clock)

        // When
        inventoryAlert.checkQuantityProduct(HOT_DOG, 3)

        // Then
        val inOrder = Mockito.inOrder(console)
        inOrder.verify(console).printLine("Alert Stock")
        inOrder.verify(console).printLine("19-04-2016")
        inOrder.verify(console).printLine("---")
        inOrder.verify(console).printLine("Product | Quantity | Alert threshold")
        inOrder.verify(console).printLine("Hot Dog | 3 | 5")
    }

    @Test
    fun `do nothing if quantity not out of stock`() {
        // Given
        val inventoryAlert = InventoryAlert(mapOf(HOT_DOG to 5), console, Clock())

        // When
        inventoryAlert.checkQuantityProduct(HOT_DOG, 10)

        // Then
        val inOrder = Mockito.inOrder(console)
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun `do nothing if there are no product thresold`() {
        // Given
        val inventoryAlert = InventoryAlert(emptyMap(), console, Clock())

        // When
        inventoryAlert.checkQuantityProduct(HOT_DOG, 10)

        // Then
        val inOrder = Mockito.inOrder(console)
        inOrder.verifyNoMoreInteractions()
    }
}

