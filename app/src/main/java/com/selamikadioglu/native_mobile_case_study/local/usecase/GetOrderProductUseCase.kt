package com.selamikadioglu.native_mobile_case_study.local.usecase

import com.selamikadioglu.native_mobile_case_study.local.repository.OrderProductRepositoryImpl
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetOrderProductUseCase @Inject constructor(private val repository: OrderProductRepositoryImpl) {
    suspend operator fun invoke(productId: String): Flow<List<Order>> {
        return repository.getOrderProduct(productId)
    }
}