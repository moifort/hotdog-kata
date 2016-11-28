package me.mottet.domain.sale

import me.mottet.domain.inventory.InventoryPrinter
import me.mottet.domain.inventory.Stock
import me.mottet.domain.receipt.ReceiptPrinter
import me.mottet.domain.sale.Product.COCA_COLAS
import me.mottet.domain.sale.Product.HOT_DOG
import me.mottet.domain.stand.Stand
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InventoryCashRegisterShould {
    @Mock lateinit var productCatalog: ProductCatalog
    @Mock lateinit var salesBook: SalesBook
    @Mock lateinit var receiptPrinter: ReceiptPrinter
    @Mock lateinit var stock: Stock
    @Mock lateinit var inventoryPrinter: InventoryPrinter


    lateinit var cashRegister: CashRegister

    @Before
    fun setUp() {
        cashRegister = CashRegister(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                productCatalog,
                stock,
                salesBook,
                receiptPrinter,
                inventoryPrinter)
    }

    @Test
    fun `get stock`() {
        // When
        cashRegister.printInventory()

        // Then
        verify(stock).allProduct()
    }

    @Test
    fun `get inventory print`() {
        // Given
        `when`(stock.allProduct()).thenReturn(mapOf(HOT_DOG to 3))

        // When
        cashRegister.printInventory()

        // Then
        verify(inventoryPrinter).print(mapOf(HOT_DOG to 3))
    }

    @Test
    fun `remove product when order`() {
        // Given
        val hotDogPrice = Price(10.0)
        val cocaPrice = Price(2.0)
        Mockito.`when`(productCatalog.priceOf(HOT_DOG)).thenReturn(hotDogPrice)
        Mockito.`when`(productCatalog.priceOf(COCA_COLAS)).thenReturn(cocaPrice)

        // When
        cashRegister.registerOrder(mapOf(HOT_DOG to 2, COCA_COLAS to 1))

        // Then
        verify(stock).removeProduct(HOT_DOG, 2)
        verify(stock).removeProduct(COCA_COLAS, 1)
    }
}

