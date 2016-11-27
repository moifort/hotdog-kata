package me.mottet.domain.sale

class ProductCatalog(private val referentialPrice: Map<Product, Price>) {

    fun priceOf(product: Product) : Price {
        val price = referentialPrice[product] ?: throw PriceNotFoundException("Price not found for $product")
        return price
    }

}

class PriceNotFoundException(override val message: String?) : RuntimeException()