package com.redditmessaging.interactors

import com.redditmessaging.model.datasource.AppState
import com.redditmessaging.model.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainInteractor(
    var repositoryRemote: Repository
) : Interactor {
    override suspend fun getData(item: Int): StateFlow<AppState> {
        return MutableStateFlow(AppState.Success(repositoryRemote.getData(item)))
    }
}

