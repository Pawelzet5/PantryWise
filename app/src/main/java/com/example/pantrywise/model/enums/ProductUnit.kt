package com.example.pantrywise.model.enums

import androidx.annotation.StringRes
import com.example.pantrywise.R

enum class ProductUnit(
    @StringRes val labelResId: Int,
    @StringRes val shortLabelResId: Int? = null
) {
    // Weight-based units
    GRAM(R.string.product_unit_gram, R.string.product_unit_short_gram),
    KILOGRAM(R.string.product_unit_kilogram, R.string.product_unit_short_kilogram),
    MILLIGRAM(R.string.product_unit_milligram, R.string.product_unit_short_milligram),
    POUND(R.string.product_unit_pound, R.string.product_unit_short_pound),
    OUNCE(R.string.product_unit_ounce, R.string.product_unit_short_ounce),
    
    // Volume-based units
    MILLILITER(R.string.product_unit_milliliter, R.string.product_unit_short_milliliter),
    LITER(R.string.product_unit_liter, R.string.product_unit_short_liter),
    TEASPOON(R.string.product_unit_teaspoon, R.string.product_unit_short_teaspoon),
    TABLESPOON(R.string.product_unit_tablespoon, R.string.product_unit_short_tablespoon),
    CUP(R.string.product_unit_cup),
    PINT(R.string.product_unit_pint),
    QUART(R.string.product_unit_quart),
    GALLON(R.string.product_unit_gallon),

    // Countable items
    PIECE(R.string.product_unit_piece),
    DOZEN(R.string.product_unit_dozen),
    
    // Packaged items
    PACK(R.string.product_unit_pack),
    BOTTLE(R.string.product_unit_bottle),
    CAN(R.string.product_unit_can),
    JAR(R.string.product_unit_jar),
    BAG(R.string.product_unit_bag),
    BOX(R.string.product_unit_box);

    companion object {
        fun getStorageUnits() = listOf(
            GRAM,
            KILOGRAM,
            MILLILITER,
            LITER,
            PIECE,
            PACK,
            CAN,
            JAR,
            BAG,
            BOX
        )
    }
}
