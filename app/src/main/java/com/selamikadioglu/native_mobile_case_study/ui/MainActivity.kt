package com.selamikadioglu.native_mobile_case_study.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.selamikadioglu.native_mobile_case_study.ui.homePage.HomePageFragment

import com.selamikadioglu.native_mobile_case_study.R
import com.selamikadioglu.native_mobile_case_study.base.BaseActivity
import com.selamikadioglu.native_mobile_case_study.base.BaseFragment
import com.selamikadioglu.native_mobile_case_study.databinding.ActivityMainBinding
import com.selamikadioglu.native_mobile_case_study.ui.basketPage.BasketPageFragment
import com.selamikadioglu.native_mobile_case_study.ui.favoritePage.FavoritePageFragment
import com.selamikadioglu.native_mobile_case_study.ui.profilePage.ProfilePageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var badgeCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(R.id.main,HomePageFragment.newInstance(), false, "")

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeIc -> {
                    replaceFragment(
                        R.id.main,
                        HomePageFragment.newInstance(),
                        false,
                    )
                    true
                }
                R.id.profileIc -> {
                    replaceFragment(
                        R.id.main,
                        ProfilePageFragment.newInstance(),
                        false,
                    )
                    true
                }
                R.id.favoriteIc ->  {
                    replaceFragment(
                        R.id.main,
                        FavoritePageFragment.newInstance(),
                        false,
                    )
                    true
                }
                R.id.basketIc -> {

                    replaceFragment(
                        R.id.main,
                        BasketPageFragment.newInstance(),
                        false,

                    )
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    fun updateBadge(count: Int) {
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.basketIc)
        badge.number = count
        badgeCount = count
        badge.isVisible = count > 0
    }
    fun getBadgeCount() : Int = badgeCount

    fun hideNavigation(hideNav: Boolean){
        if (hideNav)
            binding.bottomNavigationView.visibility = View.GONE
        else
            binding.bottomNavigationView.visibility = View.VISIBLE
    }
}