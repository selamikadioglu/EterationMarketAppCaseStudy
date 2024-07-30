package com.selamikadioglu.native_mobile_case_study.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val productId: String,
    val name: String,
    val price: String,
    val quantity: Int = 1,
)