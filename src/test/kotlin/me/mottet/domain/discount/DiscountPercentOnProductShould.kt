package me.mottet.domain.discount

import me.mottet.domain.sale.Price
import me.mottet.domain.sale.Product.COCA_COLAS
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DiscountPercentOnProductShould {

    @Test
    fun `apply discount on given price`() {
        // Given
        val discount = DiscountPercentOnProduct(COCA_COLAS, 50.0)

        // When
        val discountedPrice = discount.get(Price(10.0))

        // Then
        assertThat(discountedPrice.value).isEqualTo(5.0)
    }

    @Test
    fun `display description`() {
        assertThat( DiscountPercentOnProduct(COCA_COLAS, 50.0).description).isEqualTo("-50% Coca")
    }
}

