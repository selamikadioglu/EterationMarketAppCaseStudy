package com.selamikadioglu.native_mobile_case_study.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CheckBoxItem(
    val name: String,
    val checked: Boolean = false
) : Serializable