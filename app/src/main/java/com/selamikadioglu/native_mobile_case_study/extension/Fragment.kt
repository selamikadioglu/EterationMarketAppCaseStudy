package com.selamikadioglu.native_mobile_case_study.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


fun <T> Fragment.collectWhenStarted(sharedFlow: SharedFlow<T>, observer: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        sharedFlow.collect { t -> observer(t) }
    }
}

fun <T> Fragment.collectWhenStarted(stateFlow: StateFlow<T>, observer: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        stateFlow.collect { t -> observer(t) }
    }
}

fun <T> Fragment.collectFlow(flow: Flow<T>, observer: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { t -> observer(t) }
        }
    }
}