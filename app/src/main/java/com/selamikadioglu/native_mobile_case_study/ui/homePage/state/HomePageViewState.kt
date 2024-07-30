package com.selamikadioglu.native_mobile_case_study.ui.homePage.state

import com.selamikadioglu.native_mobile_case_study.base.BaseError
import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel

sealed class HomePageViewState {
    object Loading : HomePageViewState()
    object Empty: HomePageViewState()
    data class Success(val data: List<ProductUiModel>) :
        HomePageViewState()

    data class Error(val baseError: BaseError) :
        HomePageViewState()
}