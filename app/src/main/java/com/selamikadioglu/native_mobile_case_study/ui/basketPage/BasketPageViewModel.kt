package com.selamikadioglu.native_mobile_case_study.ui.basketPage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selamikadioglu.native_mobile_case_study.local.state.FavoriteViewState
import com.selamikadioglu.native_mobile_case_study.local.state.OrderViewState
import com.selamikadioglu.native_mobile_case_study.local.usecase.DeleteOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.GetAllOrderProductUseCase
import com.selamikadioglu.native_mobile_case_study.local.usecase.UpdateOrderProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketPageViewModel @Inject constructor(
    private val getAllOrderProductUseCase: GetAllOrderProductUseCase,
    private val updateOrderProductUseCase: UpdateOrderProductUseCase,
    private val deleteOrderProductUseCase: DeleteOrderProductUseCase
): ViewModel() {
    private var _orderGetViewState = MutableStateFlow(OrderViewState())
    val orderGetViewState = _orderGetViewState.asStateFlow()
    init {
        getOrderProducts()
    }
    fun updateProductToOrder(productId: String, quantity: Int) = viewModelScope.launch {
        updateOrderProductUseCase(productId,quantity)
    }
    fun deleteProductToOrder(productId: String) = viewModelScope.launch {
        deleteOrderProductUseCase(productId)
    }
    private fun getOrderProducts() = viewModelScope.launch {
        getAllOrderProductUseCase.invoke().collect { orderProducts ->
            _orderGetViewState.value = OrderViewState(
                isLoading = false,
                errorMessage = null,
                orderList = orderProducts
            )
        }
    }
}