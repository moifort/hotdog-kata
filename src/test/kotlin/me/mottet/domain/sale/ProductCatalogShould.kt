package me.mottet.domain.sale

import me.mottet.domain.sale.Product.COCA_COLAS
import me.mottet.domain.sale.Product.HOT_DOG
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductCatalogShould {

    @Test
    fun `return the price of the product`() {
        // Given
        val productCatalog = ProductCatalog(mapOf(HOT_DOG to Price(10.0)))

        // When
        val price = productCatalog.priceOf(HOT_DOG)

        // Then
        assertThat(price.value).isEqualTo(10.0)
    }

    @Test(expected = PriceNotFoundException::class)
    fun `return throw exception if price not found`() {
        // Given
        val productCatalog = ProductCatalog(mapOf(HOT_DOG to Price(10.0)))

        // When
        val price = productCatalog.priceOf(COCA_COLAS)

        // Then
        // Throw exception
    }
}

