package com.redditmessaging.model.repository

import com.redditmessaging.model.request.DataModel

interface Repository {
    suspend fun getData(): DataModel
}
