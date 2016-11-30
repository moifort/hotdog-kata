package me.mottet.domain.receipt

import me.mottet.domain.discount.Discount
import me.mottet.domain.sale.Order
import me.mottet.domain.sale.Sale
import me.mottet.infra.Console
import java.time.format.DateTimeFormatter
import java.util.*

class ReceiptPrinter(private val console: Console) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")!!

    fun print(sale: Sale) {
        console.printLine(sale.stand.name)
        console.printLine(sale.stand.location)
        console.printLine(formatter.format(sale.order.dateOfOrder))
        console.printLine("---")
        printOrder(sale.order)
        printDiscount(sale.discounts, sale.order)
        console.printLine("---")
        printTotal(getOrderTotal(sale.order) - getDiscountTotal(sale.discounts, sale.order))
        console.printLine("Thank your for your visit!")
    }

    private fun printOrder(order: Order) {
        console.printLine("Quantity | Description | Unit discountPrice | Total")
        order.items.forEach {
            val totalByProduct = it.quantity * it.price.value
            console.printLine("${it.quantity} | ${it.product.label}" + " | %.2f | %.2f".format(Locale.US, it.price.value,
                    totalByProduct))
        }
    }

    private fun printDiscount(discounts: Set<Discount>, order: Order) {
        if (discounts.isNotEmpty()) {
            console.printLine("Discount")
            discounts.forEach {
                val item = order.findItemBy(it.product)
                val unitDiscount = it.get(item.price).value
                val totalDiscount = unitDiscount * item.quantity
                console.printLine(" | ${it.description}" + " | %.2f | %.2f".format(Locale.US, unitDiscount, totalDiscount))
            }
        }
    }

    private fun printTotal(total: Double) = console.printLine("TOTAL USD %.2f".format(Locale.US, total))

    private fun getOrderTotal(order: Order): Double = order.items.sumByDouble { it.price.value * it.quantity }

    private fun getDiscountTotal(discounts: Set<Discount>, order: Order): Double {
        var total = 0.0
        discounts.forEach {
            val item = order.findItemBy(it.product)
            val unitDiscount = it.get(item.price).value
            val totalDiscount = unitDiscount * item.quantity
            total += totalDiscount
        }
        return total
    }
}
