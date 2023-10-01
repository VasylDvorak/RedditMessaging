package com.redditmessaging.interactors


import com.redditmessaging.model.datasource.AppState
import kotlinx.coroutines.flow.StateFlow

interface Interactor {
    suspend fun getData(page: Int): StateFlow<AppState>
}