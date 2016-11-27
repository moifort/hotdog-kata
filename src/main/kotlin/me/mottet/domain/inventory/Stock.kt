package me.mottet.domain.inventory

import me.mottet.domain.sale.Product
import java.util.*

class Stock(private val products: SortedMap<Product, Int>, private val inventoryAlert: InventoryAlert) {
    fun allProduct(): Map<Product, Int> = products

    fun removeProduct(productsByQuantity: Map<Product, Int>) =
            productsByQuantity.forEach { removeProduct(it.key, it.value) }

    fun removeProduct(product: Product, quantityToRemove: Int) {
        var quantity = products[product] ?: throw ProductNotFoundException("Product not found for $product")
        if (quantityToRemove > quantity) {
            throw ProductOutOfStockException("Product out of stock for $product")
        }
        quantity -= quantityToRemove
        products[product] = quantity
        inventoryAlert.checkQuantityProduct(product, quantity)
    }

}

class ProductNotFoundException(override val message: String) : RuntimeException()

class ProductOutOfStockException(override val message: String) : RuntimeException()