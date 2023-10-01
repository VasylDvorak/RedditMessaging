package com.redditmessaging.interactors

import com.redditmessaging.model.datasource.AppState
import com.redditmessaging.model.repository.Repository
import com.redditmessaging.model.repository.RepositoryLocal
import com.redditmessaging.model.request.DataX
import com.redditmessaging.utils.OnlineRepository
import com.redditmessaging.utils.convertListChildrenToListDataX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.getKoin


class MainInteractor(
    var repositoryRemote: Repository
) : Interactor<AppState> {
val repositoryLocal: RepositoryLocal by getKoin().inject()
    private val checkConnection: OnlineRepository by getKoin().inject()
    override suspend fun getData(
        page: Int,
        fromRemoteSource: Boolean
    ): StateFlow<AppState> {
        val isNetworkAvailable=checkConnection.currentStatus()
        var remoteDataX:DataX?
        var sizeList:Int?
        if(isNetworkAvailable) {
            val childrenList = repositoryRemote.getData().data?.children ?: listOf()
            sizeList = childrenList?.size
            repositoryLocal.putFavoriteList(convertListChildrenToListDataX(childrenList))
            remoteDataX = childrenList?.get(page)?.data
        }else{
            val listDataX = repositoryLocal.getFavoriteList()
            sizeList=listDataX.size
            remoteDataX = listDataX.get(page)
        }
        remoteDataX?.upvoteRatio= remoteDataX?.upvoteRatio?.times(100)

        return MutableStateFlow(AppState.Success(Pair(remoteDataX, sizeList)))
    }
}

