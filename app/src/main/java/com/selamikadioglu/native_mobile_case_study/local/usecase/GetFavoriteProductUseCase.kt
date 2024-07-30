package com.selamikadioglu.native_mobile_case_study.local.usecase

import com.selamikadioglu.native_mobile_case_study.local.repository.FavoriteProductRepositoryImpl
import javax.inject.Inject

class GetFavoriteProductUseCase @Inject constructor(private val repository: FavoriteProductRepositoryImpl) {
    suspend operator fun invoke(productId: String) = repository.getFavoriteProduct(productId)
}