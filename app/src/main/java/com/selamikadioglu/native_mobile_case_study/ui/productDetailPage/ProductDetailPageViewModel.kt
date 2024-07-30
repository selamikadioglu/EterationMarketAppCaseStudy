package com.selamikadioglu.native_mobile_case_study.ui.productDetailPage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selamikadioglu.native_mobile_case_study.local.usecase.AddFavoriteProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.AddOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.state.FavoriteViewState
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.local.usecase.RemoveFavoriteProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.productToResponseItem
import com.selamikadioglu.native_mobile_case_study.local.state.OrderViewState
import com.selamikadioglu.native_mobile_case_study.local.usecase.GetAllOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.GetOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.UpdateOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel
import com.selamikadioglu.native_mobile_case_study.ui.homePage.productToOrderResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailPageViewModel @Inject constructor(
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase,
    private val addOrderProductUseCase: AddOrderProductUseCase,
    private val updateOrderProductUseCase: UpdateOrderProductUseCase,
    private val getOrderProductUseCase: GetOrderProductUseCase,
    ) : ViewModel() {

    private var _orderGetViewState = MutableStateFlow(OrderViewState())
    val orderGetViewState = _orderGetViewState.asStateFlow()


    fun getOrderProduct(productId: String) = viewModelScope.launch {
        getOrderProductUseCase.invoke(productId).collect { orderProduct ->
            _orderGetViewState.value = OrderViewState(
                isLoading = false,
                errorMessage = null,
                orderList = orderProduct
            )
        }
    }

    fun addProductToFavorites(product: ProductUiModel, isFavorite : Boolean) = viewModelScope.launch {
        val favoriteProduct = productToResponseItem(product, isFavorite)
        addFavoriteProductUseCase(favoriteProduct)
    }

    fun addProductToOrder(product: Order) = viewModelScope.launch {
        val orderList = productToOrderResponseItem(product)
        addOrderProductUseCase(orderList)
    }
    fun updateProductToOrder(productId: String, quantity: Int) = viewModelScope.launch {
        updateOrderProductUseCase(productId,quantity)
    }

    fun removeFavoriteProduct(productId: String) = viewModelScope.launch {
        removeFavoriteProductUseCase(productId)
    }

}