package com.redditmessaging.model.room.message

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "MessagesTable"
)
data class MessageEntity(
    @PrimaryKey var id: Int?,
    var title: String? = "",
    var upvoteRatio: Double? = 0.0,
    var numCrossposts: Int? = 0
)

