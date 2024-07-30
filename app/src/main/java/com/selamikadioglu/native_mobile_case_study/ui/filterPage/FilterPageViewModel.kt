package com.selamikadioglu.native_mobile_case_study.ui.filterPage

import androidx.lifecycle.ViewModel
import com.selamikadioglu.native_mobile_case_study.model.CheckBoxItem
import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FilterPageViewModel @Inject constructor() : ViewModel() {
    private var _searchBrandViewState = MutableStateFlow("")
    val searchBrandViewState = _searchBrandViewState.asStateFlow()
    private var _searchModelViewState = MutableStateFlow("")
    val searchModelViewState = _searchModelViewState.asStateFlow()

    fun setBrandSearchText(text: String) {
        _searchBrandViewState.value = text
    }

    fun setModelSearchText(text: String) {
        _searchModelViewState.value = text
    }

    fun setBrandFilterSearchText(brandList: List<CheckBoxItem>): List<CheckBoxItem> {
        return brandList.filter { brand ->
            brand.name.contains(searchBrandViewState.value, ignoreCase = true) ?: false
        }
    }

    fun setModelFilterSearchText(modelList: List<CheckBoxItem>): List<CheckBoxItem> {
        return modelList.filter { product ->
            product.name.contains(searchModelViewState.value, ignoreCase = true) ?: false
        }
    }
}