package com.redditmessaging.model.datasource


import com.redditmessaging.model.request.DataModel
import org.koin.java.KoinJavaComponent.getKoin


class RetrofitImplementation : RepositoryDataSource {

    override suspend fun getData(): DataModel {
        val getService = getKoin().get<ApiService>()
        return getService.searchAsync().await()
    }
}
