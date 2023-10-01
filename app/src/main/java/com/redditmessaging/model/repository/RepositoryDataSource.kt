package com.redditmessaging.model.repository

import com.redditmessaging.model.request.DataX


interface RepositoryDataSource {


    suspend fun getData(): List<DataX> = listOf()
}
