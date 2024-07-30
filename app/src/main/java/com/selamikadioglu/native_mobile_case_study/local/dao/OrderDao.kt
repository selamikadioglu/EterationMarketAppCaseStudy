package com.selamikadioglu.native_mobile_case_study.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrderProduct(order: Order)

    @Query("DELETE FROM order_table WHERE productId = :id")
    suspend fun deleteOrder(id: String)

    @Query("UPDATE order_table SET quantity = :quantity WHERE productId = :id")
    suspend fun updateOrder(id: String, quantity: Int)

    @Query("SELECT * FROM order_table")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM order_table WHERE productId = :productId LIMIT 1")
    fun getOrder(productId: String): Flow<List<Order>>
}