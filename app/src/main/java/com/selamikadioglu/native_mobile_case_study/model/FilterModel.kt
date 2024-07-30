package com.selamikadioglu.native_mobile_case_study.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class FilterModel (
    val brandList : List<CheckBoxItem>,
    val modelList : List<CheckBoxItem>,
    val sortType : String
) : Serializable