package com.redditmessaging.model.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @field:SerializedName("children") var children: List<Children>? = listOf(),
    @field:SerializedName("dist") var dist: Int? = 0,
) : Parcelable