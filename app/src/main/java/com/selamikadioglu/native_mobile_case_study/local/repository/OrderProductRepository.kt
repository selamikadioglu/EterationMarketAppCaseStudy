package com.selamikadioglu.native_mobile_case_study.local.repository

import com.selamikadioglu.native_mobile_case_study.local.table.Order
import kotlinx.coroutines.flow.Flow


interface OrderProductRepository {
    suspend fun addOrderProduct(product: Order)
    suspend fun removeOrderProduct(productId: String)
    suspend fun updateOrderProduct(productId: String, quantity: Int)
    suspend fun getOrderProducts(): Flow<List<Order>>
    suspend fun getOrderProduct(productId: String): Flow<List<Order>>
}