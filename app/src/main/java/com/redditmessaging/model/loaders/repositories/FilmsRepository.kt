package com.redditmessaging.model.loaders.repositories
import androidx.paging.PagingData
import com.redditmessaging.model.request.DataX
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    fun getPagedUsers(): Flow<PagingData<DataX>>

}