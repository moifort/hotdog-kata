package me.mottet.domain.sale

import me.mottet.domain.stand.Stand
import me.mottet.domain.util.Clock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class SaleRepositoryShould {
    @Mock lateinit var clock: Clock

    lateinit var salesBook: SalesBook

    @Before
    fun setUp() {
        salesBook = SalesBook(clock)
    }

    @Test
    fun `store and create sale`() {
        // Given
        `when`(clock.now()).thenReturn(LocalDateTime.of(2016, 11, 3, 12, 20))
        salesBook.addSale(
                Stand("Stand A", "Location"),
                listOf(Item(Product.HOT_DOG, 2, Price(10.0))),
                emptySet())

        // When
        val sales = salesBook.allSales()

        // THen
        assertThat(sales).hasSize(1)
        assertThat(sales).contains(Sale(
                Stand("Stand A", "Location"),
                Order(listOf(Item(Product.HOT_DOG, 2, Price(10.0))), LocalDateTime.of(2016, 11, 3, 12, 20)),
                emptySet()
        ))
    }


}

