package com.redditmessaging.model.repository


import com.redditmessaging.model.request.DataX


class RepositoryImplementationLocal(private val dataSource: RepositoryDataSourceLocal) :
    RepositoryLocal{


    override suspend fun getFavoriteList(): List<DataX> {
        return dataSource.getFavoriteList()
    }

    override suspend fun putFavoriteList(listData: List<DataX> ) {
        dataSource.putFavorite(listData)
    }

}
