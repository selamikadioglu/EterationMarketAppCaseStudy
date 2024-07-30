package com.selamikadioglu.native_mobile_case_study.ui.homePage

import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.model.ProductResponseModel
import kotlin.random.Random

fun List<ProductResponseModel>.toPostUiModel(): List<ProductUiModel> {
    return this.map {
        ProductUiModel(
            createdAt = it.createdAt.orEmpty(),
            id = it.id.orEmpty(),
            name = it.name.orEmpty(),
            image = it.image.orEmpty(),
            price = it.price.orEmpty() + " ₺",
            description = it.description.orEmpty(),
            model = it.model.orEmpty(),
            brand = it.brand.orEmpty()
        )
    }
}


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

fun productToOrderResponseItem(product: Order): Order {
    return Order(
        id = Random.nextInt(),
        productId = product.productId,
        name = product.name,
        price = product.price,
        quantity = product.quantity
    )
}