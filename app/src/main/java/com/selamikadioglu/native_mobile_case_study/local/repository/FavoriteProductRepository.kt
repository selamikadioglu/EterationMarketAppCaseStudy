package com.selamikadioglu.native_mobile_case_study.local.repository

import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import kotlinx.coroutines.flow.Flow

interface FavoriteProductRepository {
    suspend fun addFavoriteProduct(product: FavoriteProduct)
    suspend fun removeFavoriteProduct(productId: String)
    suspend fun getFavoriteProduct(productId: String): FavoriteProduct?
    suspend fun getFavoriteProducts(): Flow<List<FavoriteProduct>>
}