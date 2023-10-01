package com.redditmessaging.model.room.message

import androidx.room.*

@Dao
interface MessageDao {

    @Query("SELECT * FROM MessagesTable")
    suspend fun getAll(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<MessageEntity>)

    @Update
    fun update(categories: List<MessageEntity>)
}
