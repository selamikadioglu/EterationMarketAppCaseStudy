package com.selamikadioglu.native_mobile_case_study.ui.homePage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.selamikadioglu.native_mobile_case_study.ui.homePage.adapter.HomePageListingAdapter
import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.base.BaseBindableFragment
import com.selamikadioglu.native_mobile_case_study.databinding.FragmentHomePageBinding
import com.selamikadioglu.native_mobile_case_study.extension.collectFlow
import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.model.CheckBoxItem
import com.selamikadioglu.native_mobile_case_study.model.FilterModel
import com.selamikadioglu.native_mobile_case_study.ui.MainActivity
import com.selamikadioglu.native_mobile_case_study.ui.filterPage.FilterPageActivity
import com.selamikadioglu.native_mobile_case_study.ui.homePage.state.HomePageViewState
import com.selamikadioglu.native_mobile_case_study.ui.productDetailPage.ProductDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.Serializable
import kotlin.random.Random


@AndroidEntryPoint
class HomePageFragment : BaseBindableFragment<FragmentHomePageBinding, HomePageViewModel>() {
    private lateinit var homePageListingAdapter: HomePageListingAdapter
    private lateinit var productList: List<ProductUiModel>
    private lateinit var favoriteList: List<FavoriteProduct>
    private lateinit var orderAllList: List<Order>
    private var isInit = true
    private var buyButtonClicked = false
    private lateinit var lastClickedOrder: Order
    private var lastClickedItem = 0
    private lateinit var brandList : List<CheckBoxItem>
    private lateinit var modelList : List<CheckBoxItem>
    override fun targetViewModel() = HomePageViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_home_page
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    override fun onViewReady() {
        setupSearchBar()
        setupViews()
        clicks()
    }


    private fun clicks() {
        binding.selectFilterButton.setOnClickListener {
            val mapBrandList = productList.map { productList ->
                CheckBoxItem(name = productList.brand.toString(), false)
            }
            val mapModelList = productList.map { productList ->
                CheckBoxItem(name = productList.model.toString(), false)
            }
            brandList = mapBrandList
            modelList = mapModelList

            val model = FilterModel(brandList,modelList,"dasd")

            val intent = Intent(requireContext(), FilterPageActivity::class.java)
            intent.putExtra("filterModel", model)
            startActivity(intent)


        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val filters = data?.getSerializableExtra("RESULT_FILTERS") as FilterModel
            applyFilters()
        }

    }

    private fun applyFilters() {

    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderGetAllViewState.collect { viewState ->
                if (!(viewState.orderList.isNullOrEmpty())) {
                    orderAllList = viewState.orderList
                    (activity as MainActivity).updateBadge(orderAllList.sumOf { it.quantity })
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderGetViewState.collect { viewState ->

                if (buyButtonClicked) {
                    if (viewState.orderList.isNullOrEmpty()) {
                        viewModel.addProductToOrder(lastClickedOrder)
                    } else {
                        viewLifecycleOwner.lifecycleScope.launch {
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteViewState.collect { viewState ->
                if (!(viewState.favList.isNullOrEmpty())) {
                    favoriteList = viewState.favList
                }
            }
        }
        collectFlow(viewModel.homeViewState, ::handlePosts)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchViewState.collect { searchText ->
                if (::productList.isInitialized) {
                    val newList = viewModel.setFilterSearchText(productList)
                    if (newList.isNotEmpty()) {
                        binding.rcHome.visibility = View.VISIBLE
                        binding.searchEmptyState.visibility = View.GONE
                        setList(newList)
                    } else {
                        binding.rcHome.visibility = View.GONE
                        binding.searchEmptyState.visibility = View.VISIBLE
                    }
                }

            }
        }
    }

    private fun handlePosts(viewState: HomePageViewState) {
        when (viewState) {
            is HomePageViewState.Error -> {
                Log.d("Error", viewState.baseError.message.orEmpty())
            }

            is HomePageViewState.Loading -> {
                showLoading()
            }

            is HomePageViewState.Empty -> {}
            is HomePageViewState.Success -> {

                if (isInit)
                    productList = viewState.data
                isInit = false
                setList(viewState.data)
                hideLoading()
            }
        }
    }

    private fun setupViews() {

    }

    private fun setupSearchBar() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.setSearchText(it)
                }
                return true
            }
        })
    }

    private fun setList(productResponseModel: List<ProductUiModel>) {
        if (::favoriteList.isInitialized)
            favoriteList.forEachIndexed { firstIndex, favoriteModel ->
                productResponseModel.forEachIndexed { index, productResponse ->
                    if (favoriteModel.productId == productResponse.id) {
                        productResponse.isFavorite = true
                    }
                }

            }
        homePageListingAdapter = HomePageListingAdapter(requireContext(), productResponseModel)
        binding.rcHome.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = homePageListingAdapter
        }
        homePageListingAdapter.setOnItemClickListener(object :
            HomePageListingAdapter.onItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int) {
                lastClickedItem = position
                openProductDetail(homePageListingAdapter.getItem(position))
            }

            override fun favoriteClick(position: Int) {
                var isFavorite = homePageListingAdapter.getSelectedItemFavorite(position)
                var clickedProductId = homePageListingAdapter.getSelectedItem(position)
                homePageListingAdapter.changeFavoriteStatus(position, isFavorite)
                simulateLoading()
                if (isFavorite) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.removeFavoriteProduct(homePageListingAdapter.getItem(position).id)
                    }
                } else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.addProductToFavorites(homePageListingAdapter.getItem(position))
                    }
                }

            }

            override fun buyButtonClick(position: Int) {
                buyButtonClicked = true
                homePageListingAdapter.getItem(position).let {
                    lastClickedOrder =
                        Order(id = Random.nextInt(), it.id, it.name, it.price, it.quantity)
                    viewModel.getOrderProduct(homePageListingAdapter.getItem(position).id)
                }


            }

        })
    }

    private fun openProductDetail(item: ProductUiModel) {

        (activity as MainActivity).replaceFragment(
            R.id.main,
            ProductDetailFragment.newInstance(item),
            true,
            "ProductDetailFragment",
        )

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
        private const val REQUEST_CODE_FILTER = 1
        fun newInstance() = HomePageFragment()
    }
}