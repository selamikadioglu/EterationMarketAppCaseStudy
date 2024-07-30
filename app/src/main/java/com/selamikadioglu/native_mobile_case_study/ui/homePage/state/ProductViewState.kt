package com.selamikadioglu.native_mobile_case_study.ui.homePage.state

import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel

data class ProductViewState(
    val isLoading: Boolean? = null,
    val products: List<ProductUiModel> = emptyList(),
    val isInsertDatabase : Boolean? = null,
    val modelList: List<String> = emptyList(),
    val brandList: List<String> = emptyList(),
    val errorMessage: String? = null
)