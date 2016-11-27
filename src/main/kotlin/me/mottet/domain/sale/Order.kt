package me.mottet.domain.sale

import java.time.LocalDateTime

data class Order(val items: List<Item>, val dateOfOrder: LocalDateTime) {

    fun findItemBy(product: Product) : Item = items.filter { product == it.product }.first()
}