package me.mottet.domain.discount

import me.mottet.domain.sale.Price
import me.mottet.domain.sale.Product.COCA_COLAS
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DiscountPriceOnProductShould {

    @Test
    fun `apply discount on given price`() {
        // Given
        val discount = DiscountPriceOnProduct(COCA_COLAS, Price(0.50))

        // When
        val discountedPrice = discount.get(Price(10.0))

        // Then
        assertThat(discountedPrice.value).isEqualTo(0.5)
    }

    @Test
    fun `return 0 when the reduction is bigger than original price`() {
        // Given
        val discount = DiscountPriceOnProduct(COCA_COLAS, Price(10.0))

        // When
        val discountedPrice = discount.get(Price(5.0))

        // Then
        assertThat(discountedPrice.value).isEqualTo(5.0)
    }

    @Test
    fun `display description`() {
        assertThat(DiscountPriceOnProduct(COCA_COLAS, Price(10.0)).description).isEqualTo("-10.00 Coca")
    }
}



