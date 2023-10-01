package com.redditmessaging.model.datasource

import com.redditmessaging.model.request.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface ApiService {

    @GET("r/aww/hot.json")
    fun searchAsync(): Deferred<DataModel>
}
