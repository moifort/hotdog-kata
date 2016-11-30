package me.mottet.domain.inventory

import me.mottet.domain.sale.Product
import me.mottet.infra.Clock
import me.mottet.infra.Console
import java.time.format.DateTimeFormatter

class InventoryAlert(private val alertThresthold: Map<Product, Int>,
                     private val console: Console,
                     private val clock: Clock) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")!!


    fun checkQuantityProduct(product: Product, quantity: Int) {
        if (alertThresthold.containsKey(product) && alertThresthold[product]!! > quantity) {
            console.printLine("Alert Stock")
            console.printLine(formatter.format(clock.now()))
            console.printLine("---")
            console.printLine("Product | Quantity | Alert threshold")
            console.printLine("${product.label} | $quantity | ${alertThresthold[product]}")
        }
    }
}
