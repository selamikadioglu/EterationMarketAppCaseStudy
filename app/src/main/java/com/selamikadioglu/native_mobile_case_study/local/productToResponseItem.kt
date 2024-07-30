package com.selamikadioglu.native_mobile_case_study.local

import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel

fun productToResponseItem(product: ProductUiModel, isFavorite: Boolean): FavoriteProduct {
    return FavoriteProduct(
        id = product.id?.toInt() ?: 0,
        name = product.name!!,
        price = product.price!!,
        image = product.image!!,
        description = product.description!!,
        isFavorite = isFavorite,
        brand = product.brand!!,
        createdAt = product.createdAt!!,
        model = product.model!!,
        productId = product.id!!
    )
}