package com.redditmessaging.model.repository

import com.redditmessaging.model.request.DataX


interface RepositoryDataSourceLocal : RepositoryDataSource {
    suspend fun putFavorite(listData: List<DataX>)
    suspend fun getFavoriteList(): List<DataX>
}
