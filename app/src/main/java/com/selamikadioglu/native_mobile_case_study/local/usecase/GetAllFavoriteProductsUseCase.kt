package com.selamikadioglu.native_mobile_case_study.local.usecase

import com.selamikadioglu.native_mobile_case_study.local.repository.FavoriteProductRepositoryImpl
import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteProductsUseCase @Inject constructor(private val repository: FavoriteProductRepositoryImpl) {
    suspend operator fun invoke(): Flow<List<FavoriteProduct>> {
        return repository.getFavoriteProducts()
    }
}