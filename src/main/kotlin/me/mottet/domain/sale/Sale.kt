package me.mottet.domain.sale

import me.mottet.domain.discount.Discount
import me.mottet.domain.stand.Stand

data class Sale(val stand: Stand, val order: Order, val discounts: Set<Discount> = emptySet())