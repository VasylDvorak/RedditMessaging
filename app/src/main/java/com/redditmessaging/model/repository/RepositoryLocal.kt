package com.redditmessaging.model.repository

import com.redditmessaging.model.request.DataX


interface RepositoryLocal {

    suspend fun putMessagesList(listData: List<DataX>)

    suspend fun getMessagesList(): List<DataX>
}
