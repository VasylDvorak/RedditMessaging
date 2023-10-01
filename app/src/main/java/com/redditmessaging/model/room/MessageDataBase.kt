package com.redditmessaging.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.redditmessaging.model.room.message.MessageDao
import com.redditmessaging.model.room.message.MessageEntity

@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MessageDataBase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

companion object {
    const val DB_NAME = "database.db"
}
}