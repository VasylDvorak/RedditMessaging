package com.redditmessaging.model.repository

import com.redditmessaging.model.request.DataX

interface Repository {
    suspend fun getData(page: Int): Pair<DataX?, Int?>
}
