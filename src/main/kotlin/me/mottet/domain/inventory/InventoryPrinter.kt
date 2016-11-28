package me.mottet.domain.inventory

import me.mottet.domain.sale.Product
import me.mottet.domain.util.Console

class InventoryPrinter(private val console: Console) {

    fun print(stock: Map<Product, Int>) {
        console.printLine("Inventory")
        console.printLine("---")
        console.printLine("Product | Quantity")
        stock.forEach {
            console.printLine("${it.key.label} | ${it.value}")
        }
    }

}