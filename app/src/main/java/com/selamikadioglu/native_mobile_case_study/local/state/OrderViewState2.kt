package com.selamikadioglu.native_mobile_case_study.local.state

import com.selamikadioglu.native_mobile_case_study.local.table.Order


data class OrderViewState2(
    val isLoading: Boolean? = null,
    val orderList: List<Order>? = null,
    val errorMessage: String? = null
)