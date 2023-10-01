package com.redditmessaging.model.datasource

import com.redditmessaging.model.request.DataModel

interface RepositoryDataSource {
    suspend fun getData(): DataModel = DataModel()
}
