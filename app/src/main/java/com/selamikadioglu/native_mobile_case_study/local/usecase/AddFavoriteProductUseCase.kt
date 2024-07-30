package com.selamikadioglu.native_mobile_case_study.local.usecase

import com.selamikadioglu.native_mobile_case_study.local.repository.FavoriteProductRepositoryImpl
import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import javax.inject.Inject

class AddFavoriteProductUseCase @Inject constructor(private val repository: FavoriteProductRepositoryImpl) {
    suspend operator fun invoke(product: FavoriteProduct) = repository.addFavoriteProduct(product)
}