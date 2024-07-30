package com.selamikadioglu.native_mobile_case_study.ui.favoritePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selamikadioglu.native_mobile_case_study.local.usecase.GetAllFavoriteProductsUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.RemoveFavoriteProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.state.FavoriteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePageViewModel @Inject constructor(
    private val getAllFavoriteProductsUseCase: GetAllFavoriteProductsUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase,
) : ViewModel() {
    private var _favoriteGetViewState = MutableStateFlow(FavoriteViewState())
    val favoriteViewState = _favoriteGetViewState.asStateFlow()

    init {
        getFavoriteProducts()
    }

    private fun getFavoriteProducts() = viewModelScope.launch {
        getAllFavoriteProductsUseCase.invoke().collect { favoriteProducts ->
            _favoriteGetViewState.value = FavoriteViewState(
                isLoading = false,
                errorMessage = null,
                favList = favoriteProducts
            )
        }
    }

    fun removeFavoriteProduct(productId: String) = viewModelScope.launch {
        removeFavoriteProductUseCase(productId)
    }
}