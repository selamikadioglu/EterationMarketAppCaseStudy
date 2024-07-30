package com.selamikadioglu.native_mobile_case_study.local.repository

import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.local.dao.OrderDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderProductRepositoryImpl @Inject constructor(private val dao: OrderDao) :
    OrderProductRepository {

    override suspend fun addOrderProduct(product: Order) = dao.addOrderProduct(product)

    override suspend fun removeOrderProduct(productId: String) = dao.deleteOrder(productId)

    override suspend fun updateOrderProduct(productId: String, quantity: Int) = dao.updateOrder(productId,quantity)

    override suspend fun getOrderProducts(): Flow<List<Order>> {
        return dao.getAllOrders()
    }

    override suspend fun getOrderProduct(productId: String): Flow<List<Order>> {
        return dao.getOrder(productId = productId)
    }


}