package com.redditmessaging.model.repository

import com.redditmessaging.model.request.DataX


interface RepositoryDataSourceLocal : RepositoryDataSource {
    suspend fun putMessagesList(listData: List<DataX>)
    suspend fun getMessagesList(): List<DataX>
}
