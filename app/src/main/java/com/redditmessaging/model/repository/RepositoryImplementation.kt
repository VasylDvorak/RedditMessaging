package com.redditmessaging.model.repository

import com.redditmessaging.model.datasource.RepositoryDataSource
import com.redditmessaging.model.request.DataX
import com.redditmessaging.utils.OnlineRepository
import com.redditmessaging.utils.convertListChildrenToListDataX
import org.koin.java.KoinJavaComponent

class RepositoryImplementation(private val dataSource: RepositoryDataSource) :
    Repository {
    val repositoryLocal: RepositoryLocal by KoinJavaComponent.getKoin().inject()
    private val checkConnection: OnlineRepository by KoinJavaComponent.getKoin().inject()
    override suspend fun getData(item: Int): Pair<DataX?, Int?> {
        val isNetworkAvailable = checkConnection.currentStatus()
        var remoteDataX: DataX?
        var sizeList: Int?
        if (isNetworkAvailable) {
            val childrenList = dataSource.getData().data?.children ?: listOf()
            sizeList = childrenList.size
            repositoryLocal.putMessagesList(convertListChildrenToListDataX(childrenList))
            remoteDataX = childrenList[item].data
        } else {
            val listDataX = repositoryLocal.getMessagesList()
            sizeList = listDataX.size
            remoteDataX = listDataX[item]
        }
        remoteDataX?.upvoteRatio = remoteDataX?.upvoteRatio?.times(100)
        return Pair(remoteDataX, sizeList)
    }
}

