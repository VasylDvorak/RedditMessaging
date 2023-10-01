package com.redditmessaging.interactors


import com.redditmessaging.model.datasource.AppState
import kotlinx.coroutines.flow.StateFlow

interface Interactor<T : Any> {


    suspend fun getData(page: Int, fromRemoteSource: Boolean): StateFlow<AppState>
}