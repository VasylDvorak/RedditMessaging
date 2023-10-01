package com.redditmessaging.interactors

import com.redditmessaging.model.datasource.AppState
import com.redditmessaging.model.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainInteractor(
    var repositoryRemote: Repository
) : Interactor<AppState> {

    override suspend fun getData(
        page: Int,
        fromRemoteSource: Boolean
    ): StateFlow<AppState> {
        val childrenList = repositoryRemote.getData().data?.children
        val remoteList = childrenList?.get(page)?.data

        return MutableStateFlow(AppState.Success(Pair(remoteList, childrenList?.size)))
    }
}

