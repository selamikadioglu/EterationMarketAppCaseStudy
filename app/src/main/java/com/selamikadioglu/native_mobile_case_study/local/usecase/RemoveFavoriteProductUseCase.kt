package com.selamikadioglu.native_mobile_case_study.local.usecase

import com.selamikadioglu.native_mobile_case_study.local.repository.FavoriteProductRepositoryImpl
import javax.inject.Inject

class RemoveFavoriteProductUseCase @Inject constructor(private val favoriteProductRepositoryImpl: FavoriteProductRepositoryImpl) {
    suspend operator fun invoke(productId: String) = favoriteProductRepositoryImpl.removeFavoriteProduct(productId)
}