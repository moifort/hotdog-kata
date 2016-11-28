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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class CashRegisterShould {
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
    fun `store an order`() {
        // Given
        val hotDogPrice = Price(10.0)
        val cocaPrice = Price(2.0)
        `when`(productCatalog.priceOf(HOT_DOG)).thenReturn(hotDogPrice)
        `when`(productCatalog.priceOf(COCA_COLAS)).thenReturn(cocaPrice)

        // When
        cashRegister.registerOrder(mapOf(HOT_DOG to 2, COCA_COLAS to 1))

        // Then
        verify(salesBook).addSale(
                Stand("Stand A", "35 avenue Linkon - NYC"),
                listOf(Item(HOT_DOG, 2, hotDogPrice), Item(COCA_COLAS, 1, cocaPrice)),
                emptySet())
    }

    @Test
    fun `send last sale when print`() {
        // Given
        `when`(salesBook.allSales()).thenReturn(listOf(
                Sale(Stand("Stand A", "35 avenue Linkon - NYC"),
                        Order(listOf(Item(HOT_DOG, 1, Price(10.0))), LocalDateTime.of(2016, 12, 10, 11, 0)),
                        emptySet()),
                Sale(Stand("Stand A", "35 avenue Linkon - NYC"),
                        Order(listOf(Item(HOT_DOG, 2, Price(10.0))), LocalDateTime.of(2016, 12, 11, 11, 0)),
                        emptySet())
        ))

        // When
        cashRegister.printReceipt()

        // Then
        verify(receiptPrinter).print(
                Sale(
                        Stand("Stand A", "35 avenue Linkon - NYC"),
                        Order(listOf(Item(HOT_DOG, 2, Price(10.0))), LocalDateTime.of(2016, 12, 11, 11, 0))))
    }
}

