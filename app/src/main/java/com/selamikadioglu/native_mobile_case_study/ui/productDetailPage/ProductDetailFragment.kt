package com.selamikadioglu.native_mobile_case_study.ui.productDetailPage


import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.base.BaseBindableFragment
import com.selamikadioglu.native_mobile_case_study.databinding.FragmentProductDetailBinding
import com.selamikadioglu.native_mobile_case_study.extension.clickWithDebounce
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.ui.MainActivity
import com.selamikadioglu.native_mobile_case_study.ui.homePage.HomePageFragment
import com.selamikadioglu.native_mobile_case_study.ui.homePage.ProductUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.truncate
import kotlin.random.Random

@AndroidEntryPoint
class ProductDetailFragment :
    BaseBindableFragment<FragmentProductDetailBinding, ProductDetailPageViewModel>() {
    private lateinit var productItem: ProductUiModel
    private lateinit var orderAllList: List<Order>
    private var buyButtonClicked = false
    override fun targetViewModel() = ProductDetailPageViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_product_detail

    override fun onViewReady() {
        readArgument()
        setupView()
        clicks()
        observeData()
    }
    private fun setupView() = with(binding) {
        product = productItem
        setFavIconBg(productItem.isFavorite)
        Glide.with(requireContext()).load(productItem.image).fitCenter().into(productIv)
    }

    private fun clicks() = with(binding) {
        backIcon.setOnClickListener {
            popBackStack()
        }
        addToCardButton.setOnClickListener{
            buyButtonClicked = true
            viewModel.getOrderProduct(productItem.id)
            (activity as MainActivity).updateBadge((activity as MainActivity).getBadgeCount()+1)
        }
        favoriteIv.setOnClickListener {
            if (productItem.isFavorite) {
                setFavIconBg(false)
                viewLifecycleOwner.lifecycleScope.launch {
                  viewModel.removeFavoriteProduct(productItem.id)
                }
            } else {
                setFavIconBg(true)
                viewLifecycleOwner.lifecycleScope.launch {
                   viewModel.addProductToFavorites(productItem, true)
                }
            }
        }
    }



    private fun readArgument() = with(binding) {
        arguments?.let {
            productItem =
                it.getParcelable(BUNDLE_PRODUCT_INFO)!!
        }
    }
    private fun setFavIconBg(isFavorite: Boolean) = with(binding) {
        if (isFavorite) {
            favoriteIv.setImageResource(R.drawable.ic_selected_star)

        } else {
            favoriteIv.setImageResource(R.drawable.ic_unselected_star)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderGetViewState.collect { viewState ->
                if (buyButtonClicked) {
                    if (viewState.orderList.isNullOrEmpty()) {
                        viewModel.addProductToOrder(Order(Random.nextInt(),productItem.id,productItem.name,productItem.price,productItem.quantity))
                    } else {
                        viewLifecycleOwner.lifecycleScope.apply {
                            viewModel.updateProductToOrder(
                                viewState.orderList[0].productId,
                                viewState.orderList[0].quantity + 1
                            )
                        }
                    }
                    buyButtonClicked = false
                }
            }
        }
    }



    companion object {

        const val BUNDLE_PRODUCT_INFO = "BUNDLE_PRODUCT_INFO"
        fun newInstance(item: ProductUiModel) = ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_PRODUCT_INFO, item)
            }
        }
    }
}