package com.redditmessaging.model.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataModel(
    @field:SerializedName("data") var `data`: Data? = Data()
) : Parcelable