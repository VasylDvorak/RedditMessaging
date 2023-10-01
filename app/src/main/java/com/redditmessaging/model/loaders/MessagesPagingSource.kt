package com.redditmessaging.model.loaders

import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.redditmessaging.model.request.DataX

typealias UsersPageLoader = suspend (nextPage: Int) -> Pair<DataX?, Int?>?

/**
 * Example implementation of [PagingSource].
 * It is used by [Pager] for fetching data.
 */
@Suppress("UnnecessaryVariable")

class MessagesPagingSource(
    private val loader: UsersPageLoader,
) : PagingSource<Int, DataX>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
        // get the index of page to be loaded (it may be NULL, in this case let's load the first page with index = 0)
        val pageIndex = params.key ?: 1
        return try {
            // loading the desired page of users
            val results = loader.invoke(pageIndex)?:Pair(DataX(), 0)
            // success! now we can return LoadResult.Page
            // println("pagepagepage "+ results.page!!)
            return LoadResult.Page(
                data = listOf(results.first!!),
                // index of the previous page if exists
                prevKey = if (pageIndex <= 1) null else pageIndex - 1,
                // index of the next page if exists;
                // please note that 'params.loadSize' may be larger for the first load (by default x3 times)
                nextKey = if (pageIndex >= results.second!!) null else pageIndex + 1
            )
        } catch (e: Exception) {
            // failed to load users -> need to return LoadResult.Error
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataX>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}