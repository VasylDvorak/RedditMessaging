package com.redditmessaging.model.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Children(
    @field:SerializedName("data") var `data`: DataX? = DataX()
) : Parcelable