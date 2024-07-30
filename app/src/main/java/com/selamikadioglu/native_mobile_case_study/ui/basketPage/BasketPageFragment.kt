package com.selamikadioglu.native_mobile_case_study.ui.basketPage


import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.base.BaseBindableFragment
import com.selamikadioglu.native_mobile_case_study.databinding.FragmentBasketPageBinding
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.ui.MainActivity
import com.selamikadioglu.native_mobile_case_study.ui.basketPage.adapter.BasketPageListingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketPageFragment : BaseBindableFragment<FragmentBasketPageBinding, BasketPageViewModel>() {
    private lateinit var basketPageListingAdapter: BasketPageListingAdapter
    private lateinit var orderList: List<Order>
    private var totalPrice = 0.0
    override fun targetViewModel() = BasketPageViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_basket_page

    override fun onViewReady() {
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderGetViewState.collect { viewState ->
                simulateLoading()
                if (viewState.orderList.isNullOrEmpty()) {
                    binding.rcBasket.visibility = View.VISIBLE
                    binding.emptyList.visibility = View.GONE
                } else {
                    orderList = viewState.orderList
                    totalPrice = orderList.sumOf {
                        it.price.replace("₺", "")
                            .trim().toDouble() * it.quantity
                    }
                    binding.priceTv.text = "$totalPrice ₺"
                    setList(orderList)
                }
            }
        }
    }

    private fun setList(orderModel: List<Order>) {
        basketPageListingAdapter = BasketPageListingAdapter(requireContext(), orderModel)
        binding.rcBasket.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = basketPageListingAdapter
        }
        basketPageListingAdapter.setOnItemClickListener(object :
            BasketPageListingAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

            }

            override fun plusButtonClick(position: Int) {
                simulateLoading()
                (activity as MainActivity).let { it.updateBadge(it.getBadgeCount() + 1) }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.updateProductToOrder(
                        basketPageListingAdapter.getItem(position).productId,
                        basketPageListingAdapter.getItem(position).quantity + 1
                    )
                }
                basketPageListingAdapter.notifyItemChanged(position)
            }

            override fun minusButtonClick(position: Int) {
                simulateLoading()
                (activity as MainActivity).let {
                    it.updateBadge(it.getBadgeCount() - 1)
                    if (it.getBadgeCount() == 0) {
                        binding.rcBasket.visibility = View.GONE
                        binding.emptyList.visibility = View.VISIBLE
                    }
                }

                if (basketPageListingAdapter.getItem(position).quantity - 1 == 0) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.deleteProductToOrder(
                            basketPageListingAdapter.getItem(position).productId
                        )
                    }
                } else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.updateProductToOrder(
                            basketPageListingAdapter.getItem(position).productId,
                            basketPageListingAdapter.getItem(position).quantity - 1
                        )
                    }

                }
                basketPageListingAdapter.notifyItemChanged(position)
            }
        })

    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun simulateLoading() {
        showLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
        }, 100)
    }

    companion object {
        fun newInstance() = BasketPageFragment()
    }

}