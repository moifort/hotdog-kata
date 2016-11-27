package me.mottet.domain.sale

import me.mottet.domain.discount.DiscountPercentOnProduct
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DiscountCashRegisterShould {
    @Mock lateinit var productCatalog: ProductCatalog
    @Mock lateinit var saleRepository: SaleRepository
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
                saleRepository,
                receiptPrinter,
                inventoryPrinter)
    }

    @Test
    fun `store discount`() {
        // Given
        val hotDogPrice = Price(10.0)
        val cocaPrice = Price(2.0)
        `when`(productCatalog.priceOf(HOT_DOG)).thenReturn(hotDogPrice)
        `when`(productCatalog.priceOf(COCA_COLAS)).thenReturn(cocaPrice)

        // When
        cashRegister.registerOrder(mapOf(HOT_DOG to 2, COCA_COLAS to 1),
                setOf(DiscountPercentOnProduct(COCA_COLAS, 10.0)))

        // Then
        verify(saleRepository).addSale(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                listOf(Item(HOT_DOG, 2, hotDogPrice), Item(COCA_COLAS, 1, cocaPrice)),
                setOf(DiscountPercentOnProduct(COCA_COLAS, 10.0)))
    }

    @Test
    fun `store empty discount`() {
        // Given
        val hotDogPrice = Price(10.0)
        val cocaPrice = Price(2.0)
        `when`(productCatalog.priceOf(HOT_DOG)).thenReturn(hotDogPrice)
        `when`(productCatalog.priceOf(COCA_COLAS)).thenReturn(cocaPrice)

        // When
        cashRegister.registerOrder(mapOf(HOT_DOG to 2, COCA_COLAS to 1))

        // Then
        verify(saleRepository).addSale(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                listOf(Item(HOT_DOG, 2, hotDogPrice), Item(COCA_COLAS, 1, cocaPrice)),
                emptySet())
    }

}

