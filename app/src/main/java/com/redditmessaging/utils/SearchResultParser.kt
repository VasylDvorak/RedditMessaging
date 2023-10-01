package com.redditmessaging.utils

import com.redditmessaging.model.request.Children
import com.redditmessaging.model.request.DataX


fun resultPairParser(children: MutableList<Children>?): MutableList<DataX> {
    var outputResults: MutableList<DataX> = mutableListOf()

    if (!children.isNullOrEmpty()) {
 children.forEach{ child->
     child.data?.let { outputResults.add(it) }
 }
        outputResults.forEach{
            it.upvoteRatio = it.upvoteRatio?.times(100)
        }
        return outputResults
    }else{
       return mutableListOf()
    }
}

