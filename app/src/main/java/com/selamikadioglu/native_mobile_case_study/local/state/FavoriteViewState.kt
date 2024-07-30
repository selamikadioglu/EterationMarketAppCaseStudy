package com.selamikadioglu.native_mobile_case_study.local.state

import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct

data class FavoriteViewState(
    val isLoading: Boolean? = null,
    val favList: List<FavoriteProduct>? = null,
    val errorMessage: String? = null
)