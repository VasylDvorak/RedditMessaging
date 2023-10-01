package com.redditmessaging.utils

import com.redditmessaging.model.request.Children
import com.redditmessaging.model.request.DataX
import com.redditmessaging.model.room.message.MessageEntity


fun converterDataXToMessageEntity(listData: List<DataX>) =

    listData.map {
        MessageEntity(
            id = listData.indexOf(it),
            title = it.title,
            upvoteRatio = it.upvoteRatio,
            numCrossposts = it.numCrossposts
        )
    }

fun converterMessageEntityToDataX(listMessageEntity: List<MessageEntity>) =
    listMessageEntity.map {
        DataX(
            title = it.title,
            upvoteRatio = it.upvoteRatio,
            numCrossposts = it.numCrossposts
        )
    }

fun convertListChildrenToListDataX(listChildren: List<Children>): List<DataX> {
    listChildren.toMutableList()
    var output = mutableListOf<DataX>()
    listChildren.forEach {
        it.data?.let { it1 -> output.add(it1) }
    }
    return output
}