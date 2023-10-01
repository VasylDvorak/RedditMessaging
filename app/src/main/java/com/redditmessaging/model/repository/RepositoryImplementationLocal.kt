package com.redditmessaging.model.repository


import com.redditmessaging.model.request.DataX


class RepositoryImplementationLocal(private val dataSource: RepositoryDataSourceLocal) :
    RepositoryLocal {


    override suspend fun getMessagesList(): List<DataX> {
        return dataSource.getMessagesList()
    }

    override suspend fun putMessagesList(listData: List<DataX>) {
        dataSource.putMessagesList(listData)
    }

}
