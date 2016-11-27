package me.mottet.domain.sale

import me.mottet.domain.discount.Discount
import me.mottet.domain.inventory.InventoryPrinter
import me.mottet.domain.inventory.Stock
import me.mottet.domain.receipt.ReceiptPrinter
import me.mottet.domain.stand.Stand

class CashRegister(private val stand: Stand,
                   private val productCatalog: ProductCatalog,
                   private val stock: Stock,
                   private val saleRepository: SaleRepository,
                   private val receiptPrinter: ReceiptPrinter,
                   private val inventoryPrinter: InventoryPrinter) {

    fun registerOrder(order: Map<Product, Int>) {
        registerOrder(order, emptySet())
    }

    fun registerOrder(order: Map<Product, Int>, discounts: Set<Discount>) {
        saleRepository.addSale(stand, itemsOf(order), discounts)
        stock.removeProduct(order)
    }

    private fun itemsOf(order: Map<Product, Int>): List<Item> {
        return order.map { Item(it.key, it.value, productCatalog.priceOf(it.key)) }
    }

    fun printReceipt() {
        val currentSale = saleRepository.allSales().last()
        receiptPrinter.print(currentSale)
    }

    fun printInventory() {
        inventoryPrinter.print(stock.allProduct())
    }
}

