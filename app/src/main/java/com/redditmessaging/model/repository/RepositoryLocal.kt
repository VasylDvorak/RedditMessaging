package com.redditmessaging.model.repository

import com.redditmessaging.model.request.DataX


interface RepositoryLocal{

    suspend fun putFavoriteList(listData: List<DataX>)

    suspend fun getFavoriteList(): List<DataX>
}
