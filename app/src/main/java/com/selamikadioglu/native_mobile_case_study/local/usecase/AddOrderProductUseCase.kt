package com.selamikadioglu.native_mobile_case_study.local.usecase

import com.selamikadioglu.native_mobile_case_study.local.repository.OrderProductRepositoryImpl
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import javax.inject.Inject

class AddOrderProductUseCase @Inject constructor(private val repository: OrderProductRepositoryImpl) {
    suspend operator fun  invoke(product: Order) = repository.addOrderProduct(product)
}