package com.selamikadioglu.native_mobile_case_study.local.usecase

import com.selamikadioglu.native_mobile_case_study.local.repository.OrderProductRepositoryImpl
import javax.inject.Inject

class UpdateOrderProductUseCase @Inject constructor(private val repository: OrderProductRepositoryImpl) {
    suspend operator fun invoke(productId: String, quantity: Int) {
        return repository.updateOrderProduct(productId,quantity)
    }
}