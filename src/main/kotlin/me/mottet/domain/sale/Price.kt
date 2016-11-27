package me.mottet.domain.sale

data class Price(val value: Double) {
    init {
        if (value < 0){
            throw PriceCannotBeNegativeException("value: $value")
        }
    }

}

class PriceCannotBeNegativeException(override val message: String) : RuntimeException()
