package me.mottet.domain.inventory

import me.mottet.domain.sale.Product
import me.mottet.domain.util.Clock
import me.mottet.domain.util.Console
import java.time.format.DateTimeFormatter

class InventoryPrinter(private val console: Console,
                       private val clock: Clock) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    fun print(stock: Map<Product, Int>) {
        console.printLine("Inventory")
        console.printLine(formatter.format(clock.now()))
        console.printLine("---")
        console.printLine("Product | Quantity")
        stock.forEach {
            console.printLine("${it.key.label} | ${it.value}")
        }
    }

}