package com.selamikadioglu.native_mobile_case_study.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


abstract class BaseFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onStop() {
        super.onStop()
    }

    protected fun fragmentTransaction(): FragmentTransaction {
        return requireFragmentManager().beginTransaction()
    }


    fun popBackStack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    fun popBackStack(tag: String) {
        requireActivity().supportFragmentManager.popBackStack(tag, 0)
    }

}
