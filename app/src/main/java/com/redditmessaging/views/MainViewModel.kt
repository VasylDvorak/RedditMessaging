package com.redditmessaging.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.redditmessaging.model.loaders.repositories.MessagesRepository
import com.redditmessaging.model.request.DataX
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel(private val usersRepository: MessagesRepository) : ViewModel() {

    val messagesFlow: Flow<PagingData<DataX>>

    init {
        messagesFlow = usersRepository.getPagedMessages().cachedIn(viewModelScope)
            // always use cacheIn operator for flows returned by Pager. Otherwise exception may be thrown
            // when 1) refreshing/invalidating or 2) subscribing to the flow more than once.

    }

}
