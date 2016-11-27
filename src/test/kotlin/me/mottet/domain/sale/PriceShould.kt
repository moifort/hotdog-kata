package me.mottet.domain.discount

import me.mottet.domain.sale.Price
import me.mottet.domain.sale.PriceCannotBeNegativeException
import org.junit.Test

class PriceShould {

    @Test(expected = PriceCannotBeNegativeException::class)
    fun `throw exception if bellow 0`() {
        Price(-10.0)
    }

}

