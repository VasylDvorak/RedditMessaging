package com.redditmessaging.model.datasource

import android.os.Parcelable
import com.redditmessaging.model.request.DataX
import kotlinx.android.parcel.Parcelize

@Parcelize
sealed class AppState : Parcelable {
    @Parcelize
    data class Success(val data: Pair<DataX?, Int?>) : AppState(), Parcelable

    @Parcelize
    data class Error(val error: Throwable) : AppState(), Parcelable

    @Parcelize
    data class Loading(val progress: Int?) : AppState(), Parcelable
}
