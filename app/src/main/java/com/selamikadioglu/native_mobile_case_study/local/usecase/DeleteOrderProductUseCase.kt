package com.selamikadioglu.native_mobile_case_study.local.usecase

import com.selamikadioglu.native_mobile_case_study.local.repository.OrderProductRepositoryImpl
import javax.inject.Inject

class DeleteOrderProductUseCase @Inject constructor(private val repository: OrderProductRepositoryImpl) {
    suspend operator fun invoke(productId: String) {
        return repository.removeOrderProduct(productId)
    }
}