package me.mottet.domain.discount

import me.mottet.domain.sale.Price
import me.mottet.domain.sale.Product

data class DiscountPercentOnProduct(override val product: Product, val percent: Double) : Discount {
    override val description: String get() = "-%.0f%% ${product.label}".format(percent)

    override fun get(price: Price): Price {
        return Price(price.value * percent / 100)
    }

}

