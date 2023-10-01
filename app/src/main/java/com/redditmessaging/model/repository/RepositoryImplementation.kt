package com.redditmessaging.model.repository

import com.redditmessaging.model.datasource.RepositoryDataSource
import com.redditmessaging.model.request.DataModel
import com.redditmessaging.model.request.DataX

class RepositoryImplementation(private val dataSource: RepositoryDataSource) :
    Repository {

    override suspend fun getData(): DataModel{
        return dataSource.getData()
    }


}

