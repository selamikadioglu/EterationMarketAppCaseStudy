package com.selamikadioglu.native_mobile_case_study.ui.filterPage


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.base.BaseBindableFragment
import com.selamikadioglu.native_mobile_case_study.databinding.FragmentFilterPageBinding
import com.selamikadioglu.native_mobile_case_study.model.CheckBoxItem
import com.selamikadioglu.native_mobile_case_study.model.FilterModel
import com.selamikadioglu.native_mobile_case_study.ui.filterPage.adapter.BrandAdapter
import com.selamikadioglu.native_mobile_case_study.ui.filterPage.adapter.ModelAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterPageFragment : BaseBindableFragment<FragmentFilterPageBinding, FilterPageViewModel>() {
    private lateinit var brandListingAdapter: BrandAdapter
    private lateinit var modelListingAdapter: ModelAdapter
    private lateinit var filterModel: FilterModel
    private lateinit var brandList: List<CheckBoxItem>
    private lateinit var modelList: List<CheckBoxItem>

    override fun targetViewModel() = FilterPageViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_filter_page

    override fun onViewReady() {
        readArgument()
        clicks()
        observeData()
        setupBrandSearchBar()
        setupModelSearchBar()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchBrandViewState.collect { searchText ->
                val newBrandList = viewModel.setBrandFilterSearchText(brandList = brandList)
                setBrandList(newBrandList.distinct())
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchModelViewState.collect { searchText ->
                val newModelList = viewModel.setModelFilterSearchText(modelList = modelList)
                setModelList(newModelList.distinct())
            }

        }
    }


    private fun setBrandList(brandList: List<CheckBoxItem>) {
        brandListingAdapter = BrandAdapter(requireContext(), brandList)
        binding.brandRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = brandListingAdapter
        }
        brandListingAdapter.setOnItemClickListener(object :
            BrandAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setModelList(modelList: List<CheckBoxItem>) {
        modelListingAdapter = ModelAdapter(requireContext(), modelList)
        binding.modelRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = modelListingAdapter
        }
        modelListingAdapter.setOnItemClickListener(object :
            ModelAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setupBrandSearchBar() {
        binding.brandSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.setBrandSearchText(it)
                }
                return true
            }
        })
    }
    private fun setupModelSearchBar() {
        binding.modelSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.setModelSearchText(it)
                }
                return true
            }
        })
    }

    private fun clicks() {
        binding.closeButton.setOnClickListener {
            requireActivity().finish()
        }
        binding.applyFiltersButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("RESULT_FILTERS", FilterModel(listOf(CheckBoxItem("name",false)),listOf(CheckBoxItem("name",false)),"sada"))
            }
            requireActivity().setResult(Activity.RESULT_OK, resultIntent)
            requireActivity().finish()
        }
    }

    private fun readArgument() = with(binding) {
        arguments?.let {
            filterModel =
                (it.getSerializable(BUNDLE_FILTER_INFO) as FilterModel?)!!
        }
        brandList = filterModel.brandList
        modelList = filterModel.modelList
        setBrandList(filterModel.brandList)
        setModelList(filterModel.modelList)
    }

    companion object {
        const val BUNDLE_FILTER_INFO = "BUNDLE_FILTER_INFO"
        fun newInstance(item: FilterModel) = FilterPageFragment().apply {
            arguments = Bundle().apply {
                putSerializable(BUNDLE_FILTER_INFO, item)
            }
        }
    }
}