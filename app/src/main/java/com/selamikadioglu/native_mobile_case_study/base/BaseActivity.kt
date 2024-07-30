package com.selamikadioglu.native_mobile_case_study.base

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.selamikadioglu.native_mobile_case_study.R

open class BaseActivity : AppCompatActivity() {
    private var actionBarTitle: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun closeActivity() {
        finish()
    }

    fun hideActionBar() {
        supportActionBar?.hide()
    }

    protected fun fragmentManager(): FragmentManager {
        return supportFragmentManager
    }

    protected fun fragmentTransaction(): FragmentTransaction {
        return fragmentManager().beginTransaction()
    }
    fun replaceFragment(
        activityId: Int,
        fragment: BaseFragment,
        addToBackStack: Boolean = false,
        tag: String? = null,

    ) {
        val transaction = fragmentTransaction().replace(activityId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }
    companion object {
        const val NONE = -1
        val TAG = BaseActivity::class.java.name
    }
}