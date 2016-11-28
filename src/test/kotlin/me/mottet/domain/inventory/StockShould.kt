package me.mottet.domain.inventory

import me.mottet.domain.sale.Product.*

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
@RunWith(MockitoJUnitRunner::class)
class StockShould {
    @Mock lateinit var inventoryAlert: InventoryAlert

    lateinit var stock: Stock

    @Before
    fun setUp() {
        stock = Stock(sortedMapOf(HOT_DOG to 20, COCA_COLAS to 20), inventoryAlert)
    }

    @Test
    fun `stock product and quantity`() {
        // Then
        assertThat(stock.allProduct()).isEqualTo(mapOf(HOT_DOG to 20, COCA_COLAS to 20))
    }

    @Test
    fun `remove product quantity`() {
        // When
        stock.removeProduct(HOT_DOG, 2)

        // Then
        assertThat(stock.allProduct()).isEqualTo(mapOf(HOT_DOG to 18, COCA_COLAS to 20))
    }

    @Test(expected = ProductNotFoundException::class)
    fun `remove product who dont exist`() {

        // When
        stock.removeProduct(COOKIE, 2)
    }

    @Test(expected = ProductOutOfStockException::class)
    fun `remove product who is empty`() {
        // When
        stock.removeProduct(HOT_DOG, 23)
    }


    @Test
    fun `check quantity product`() {
        // Given
        val stock = Stock(sortedMapOf(HOT_DOG to 20, COCA_COLAS to 20), inventoryAlert)

        // When
        stock.removeProduct(HOT_DOG, 2)

        // Then
        Mockito.verify(inventoryAlert).checkQuantityProduct(HOT_DOG, 18)
    }
}