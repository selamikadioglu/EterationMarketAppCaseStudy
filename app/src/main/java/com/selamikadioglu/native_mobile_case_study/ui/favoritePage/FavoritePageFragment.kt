package com.selamikadioglu.native_mobile_case_study.ui.favoritePage

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.base.BaseBindableFragment
import com.selamikadioglu.native_mobile_case_study.databinding.FragmentFavoritePageBinding
import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import com.selamikadioglu.native_mobile_case_study.local.table.Order
import com.selamikadioglu.native_mobile_case_study.ui.favoritePage.adapter.FavoritePageAdapter
import com.selamikadioglu.native_mobile_case_study.ui.homePage.adapter.HomePageListingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritePageFragment :
    BaseBindableFragment<FragmentFavoritePageBinding, FavoritePageViewModel>() {

    private lateinit var favoritePageAdapter: FavoritePageAdapter
    private lateinit var favoriteList: List<FavoriteProduct>
    override fun targetViewModel() = FavoritePageViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_favorite_page

    override fun onViewReady() {
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteViewState.collect { viewState ->
                if (!(viewState.favList.isNullOrEmpty())) {
                    favoriteList = viewState.favList
                    setList(favoriteList)
                }
            }
        }
    }

    private fun setList(productResponseModel: List<FavoriteProduct>) {

        favoritePageAdapter = FavoritePageAdapter(requireContext(), productResponseModel)
        binding.rcFavorite.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = favoritePageAdapter
        }
        favoritePageAdapter.setOnItemClickListener(object :
            FavoritePageAdapter.onItemClickListener {
            override fun favoriteClick(position: Int) {
                viewModel.removeFavoriteProduct(favoritePageAdapter.getItem(position).productId)
            }
        })
    }

    companion object {
        fun newInstance() = FavoritePageFragment()
    }

}