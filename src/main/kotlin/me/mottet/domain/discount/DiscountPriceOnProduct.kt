package me.mottet.domain.discount

import me.mottet.domain.sale.Price
import me.mottet.domain.sale.Product
import java.util.*

data class DiscountPriceOnProduct(override val product: Product, val discountPrice: Price) : Discount {
    override val description: String get() = "-%.2f ${product.label}".format(Locale.US, discountPrice.value)


    override fun get(price: Price): Price {
        if (this.discountPrice.value > price.value) {
            return Price(price.value)
        }
        return Price(this.discountPrice.value)
    }
}
