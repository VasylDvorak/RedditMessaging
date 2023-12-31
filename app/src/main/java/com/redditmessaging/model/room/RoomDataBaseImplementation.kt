package com.redditmessaging.model.room

import com.redditmessaging.model.repository.RepositoryDataSourceLocal
import com.redditmessaging.model.request.DataX
import com.redditmessaging.model.room.message.MessageDao
import com.redditmessaging.utils.converterDataXToMessageEntity
import com.redditmessaging.utils.converterMessageEntityToDataX


class RoomDataBaseImplementation(
    private val messageDao: MessageDao
) : RepositoryDataSourceLocal {
    override suspend fun putMessagesList(listData: List<DataX>) {

        messageDao.insertAll(converterDataXToMessageEntity(listData))
    }

    override suspend fun getMessagesList(): List<DataX> {
        return converterMessageEntityToDataX(messageDao.getAll())
    }

}
