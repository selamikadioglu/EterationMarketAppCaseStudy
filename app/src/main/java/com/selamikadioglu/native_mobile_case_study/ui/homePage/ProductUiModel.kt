package com.selamikadioglu.native_mobile_case_study.ui.homePage

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import com.selamikadioglu.native_mobile_case_study.extension.orZero


data class ProductUiModel(
    val createdAt: String,
    val name: String,
    val image: String,
    val price: String,
    val description: String,
    val model: String,
    val brand: String,
    var isFavorite: Boolean = false,
    val id: String,
    val quantity: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readInt().orZero()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdAt)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(price)
        parcel.writeString(description)
        parcel.writeString(model)
        parcel.writeString(brand)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeString(id)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductUiModel> {
        override fun createFromParcel(parcel: Parcel): ProductUiModel {
            return ProductUiModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductUiModel?> {
            return arrayOfNulls(size)
        }
    }
}