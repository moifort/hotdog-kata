package me.mottet.domain

import me.mottet.domain.discount.DiscountPercentOnProduct
import me.mottet.domain.discount.DiscountPriceOnProduct
import me.mottet.domain.inventory.InventoryAlert
import me.mottet.domain.inventory.InventoryPrinter
import me.mottet.domain.inventory.Stock
import me.mottet.domain.receipt.ReceiptPrinter
import me.mottet.domain.sale.CashRegister
import me.mottet.domain.sale.Price
import me.mottet.domain.sale.Product.COCA_COLAS
import me.mottet.domain.sale.Product.HOT_DOG
import me.mottet.domain.sale.ProductCatalog
import me.mottet.domain.sale.SaleRepository
import me.mottet.domain.stand.Stand
import me.mottet.domain.util.Clock
import me.mottet.domain.util.Console

fun main(args: Array<String>) {
    val cashRegisterStandA = CashRegister(
            Stand("Stand A", "35 avenue Linkon - NYC"),
            ProductCatalog(mapOf(HOT_DOG to Price(7.0), COCA_COLAS to Price(2.0))),
            Stock(sortedMapOf(HOT_DOG to 20, COCA_COLAS to 20), InventoryAlert(
                    mapOf(HOT_DOG to 10, COCA_COLAS to 10),
                    Console(),
                    Clock())),
            SaleRepository(Clock()),
            ReceiptPrinter(Console()),
            InventoryPrinter(Console(), Clock()))

    // Inventory
    cashRegisterStandA.printInventory()

    println()
    println()

    // First order
    cashRegisterStandA.registerOrder(mapOf(HOT_DOG to 14, COCA_COLAS to 14))
    cashRegisterStandA.printReceipt()

    println()
    println()

    // Second order
    cashRegisterStandA.registerOrder(mapOf(HOT_DOG to 2, COCA_COLAS to 3),
            setOf(DiscountPercentOnProduct(HOT_DOG, 50.0), DiscountPriceOnProduct(COCA_COLAS, Price(1.0))))
    cashRegisterStandA.printReceipt()

    println()
    println()

    // Inventory
    cashRegisterStandA.printInventory()

    val cashRegisterStandB = CashRegister(
            Stand("Stand B", "3 Baker street - NYC"),
            ProductCatalog(mapOf(HOT_DOG to Price(7.0), COCA_COLAS to Price(2.0))),
            Stock(sortedMapOf(HOT_DOG to 120, COCA_COLAS to 120), InventoryAlert(mapOf(HOT_DOG to 10, COCA_COLAS to 10), Console(), Clock())),
            SaleRepository(Clock()),
            ReceiptPrinter(Console()),
            InventoryPrinter(Console(), Clock()))

    // Inventory
    cashRegisterStandB.printInventory()

    println()
    println()

    // First order
    cashRegisterStandB.registerOrder(mapOf(HOT_DOG to 7, COCA_COLAS to 10))
    cashRegisterStandB.printReceipt()

    println()
    println()

    // Second order
    cashRegisterStandB.registerOrder(mapOf(HOT_DOG to 2, COCA_COLAS to 3),
            setOf(DiscountPercentOnProduct(HOT_DOG, 50.0), DiscountPriceOnProduct(COCA_COLAS, Price(1.0))))
    cashRegisterStandB.printReceipt()

    println()
    println()

    // Inventory
    cashRegisterStandB.printInventory()
}
