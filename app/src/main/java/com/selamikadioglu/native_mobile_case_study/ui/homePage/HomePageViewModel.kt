package com.selamikadioglu.native_mobile_case_study.ui.homePage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selamikadioglu.native_mobile_case_study.base.ApiResult
import com.selamikadioglu.native_mobile_case_study.local.usecase.AddFavoriteProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.AddOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.state.FavoriteViewState
import com.selamikadioglu.native_mobile_case_study.local.usecase.GetAllFavoriteProductsUseCase
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.local.usecase.RemoveFavoriteProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.state.OrderViewState
import com.selamikadioglu.native_mobile_case_study.local.state.OrderViewState2
import com.selamikadioglu.native_mobile_case_study.local.usecase.GetAllOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.GetOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.UpdateOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.repository.ServiceRepository
import com.selamikadioglu.native_mobile_case_study.ui.homePage.state.HomePageViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val apiRepository: ServiceRepository,
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase,
    private val getAllFavoriteProductsUseCase: GetAllFavoriteProductsUseCase,
    private val addOrderProductUseCase: AddOrderProductUseCase,
    private val getOrderProductUseCase: GetOrderProductUseCase,
    private val getAllOrderProductUseCase: GetAllOrderProductUseCase,
    private val updateOrderProductUseCase: UpdateOrderProductUseCase

) : ViewModel() {
    private val _homeViewState = MutableStateFlow<HomePageViewState>(HomePageViewState.Loading)
    val homeViewState = _homeViewState.asStateFlow()
    private var _searchViewState = MutableStateFlow("")
    val searchViewState = _searchViewState.asStateFlow()
    private var _favoriteGetViewState = MutableStateFlow(FavoriteViewState())
    val favoriteViewState = _favoriteGetViewState.asStateFlow()
    private var _orderGetViewState = MutableStateFlow(OrderViewState())
    val orderGetViewState = _orderGetViewState.asStateFlow()
    private var _orderGetAllViewState = MutableStateFlow(OrderViewState2())
    val orderGetAllViewState = _orderGetAllViewState.asStateFlow()
    init {
        fetchPostServices()
        getFavoriteProducts()
        getOrderProducts()
    }
    private fun getOrderProducts() = viewModelScope.launch {
        getAllOrderProductUseCase.invoke().collect { orderProducts ->
            _orderGetAllViewState.value = OrderViewState2(
                isLoading = false,
                errorMessage = null,
                orderList = orderProducts
            )
        }
    }
    private fun fetchPostServices() {
        viewModelScope.launch {
            apiRepository.getData().collect { result ->
                val apiResult = when (result) {
                    is ApiResult.Empty -> HomePageViewState.Empty
                    is ApiResult.Error -> HomePageViewState.Error(result.error)
                    is ApiResult.Loading -> HomePageViewState.Loading
                    is ApiResult.Success -> {
                        HomePageViewState.Success(
                            result.data.toPostUiModel()
                        )
                    }
                }
                _homeViewState.emit(apiResult)
            }
        }
    }

    fun addProductToFavorites(product: ProductUiModel) = viewModelScope.launch {
        val favoriteProduct = productToResponseItem(product, true)
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

    private fun getFavoriteProducts() = viewModelScope.launch {
        getAllFavoriteProductsUseCase.invoke().collect { favoriteProducts ->
            _favoriteGetViewState.value = FavoriteViewState(
                isLoading = false,
                errorMessage = null,
                favList = favoriteProducts
            )
        }
    }

    fun getOrderProduct(productId: String) = viewModelScope.launch {
        getOrderProductUseCase.invoke(productId).collect { orderProduct ->
            _orderGetViewState.value = OrderViewState(
                isLoading = false,
                errorMessage = null,
                orderList = orderProduct
            )
        }
    }

    fun setSearchText(text: String) {
        _searchViewState.value = text
    }

    fun setFilterSearchText(productList: List<ProductUiModel>): List<ProductUiModel> {
        return productList.filter { product ->
            product.name.contains(searchViewState.value, ignoreCase = true) ?: false
        }
    }

}