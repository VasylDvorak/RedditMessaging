package com.redditmessaging.model.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataX(
    @field:SerializedName("title") var title: String? = "",
    @field:SerializedName("upvote_ratio") var upvoteRatio: Double? = 0.0,
    @field:SerializedName("num_crossposts") var numCrossposts: Int? = 0,
): Parcelable