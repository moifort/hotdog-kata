package me.mottet.domain.discount

import me.mottet.domain.sale.Price
import me.mottet.domain.sale.Product

interface Discount {
    val product: Product
    val description: String

    fun get(price: Price): Price
}