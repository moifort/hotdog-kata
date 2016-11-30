package me.mottet.domain.sale

import me.mottet.domain.discount.Discount
import me.mottet.domain.stand.Stand
import me.mottet.infra.Clock

class SalesBook(private val clock: Clock) {
    private val sales = mutableListOf<Sale>()

    fun addSale(stand: Stand, items: List<Item>, discounts: Set<Discount>) =
            sales.add(Sale(stand, Order(items, clock.now()), discounts))

    fun allSales(): List<Sale> = sales
}

