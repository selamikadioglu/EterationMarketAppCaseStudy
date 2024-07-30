package com.selamikadioglu.native_mobile_case_study.ui.filterPage


import android.os.Bundle
import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.base.BaseActivity
import com.selamikadioglu.native_mobile_case_study.base.BaseFragment
import com.selamikadioglu.native_mobile_case_study.model.FilterModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterPageActivity : BaseActivity() {
    private lateinit var filterModel: FilterModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_page)
        filterModel = (intent.getSerializableExtra("filterModel") as? FilterModel)!!

        replaceFragment(
            R.id.filterActivity,
            FilterPageFragment.newInstance(filterModel),
            false,
            "FilterPageFragment",
        )

    }
}