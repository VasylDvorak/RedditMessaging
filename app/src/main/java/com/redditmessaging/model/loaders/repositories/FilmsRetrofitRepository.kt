package com.redditmessaging.model.loaders.repositories


import android.content.Context
import android.widget.Toast
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.redditmessaging.R
import com.redditmessaging.interactors.MainInteractor
import com.redditmessaging.model.datasource.AppState
import com.redditmessaging.model.datasource.RetrofitImplementation
import com.redditmessaging.model.loaders.FilmsPagingSource
import com.redditmessaging.model.loaders.UsersPageLoader
import com.redditmessaging.model.repository.RepositoryImplementation
import com.redditmessaging.model.request.DataX
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.getKoin

class FilmsRetrofitRepository(
    private val ioDispatcher: CoroutineDispatcher
) : FilmsRepository {
    val interactor = MainInteractor(RepositoryImplementation(RetrofitImplementation()))
    override fun getPagedUsers(): Flow<PagingData<DataX>> {
        val loader: UsersPageLoader = { nextPage ->
            dataModel (nextPage, true)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FilmsPagingSource(loader) }
        ).flow
    }


    private suspend fun dataModel(page: Int, isOnline: Boolean): Pair<DataX?, Int?>? {
        val appState = getResults( page, isOnline)

        when (appState) {
            is AppState.Success -> {
                val output = appState.data
                if ((output.first==null)||(output.second==0)) {
                    val contextApp = getKoin().get<Context>()
                    Toast.makeText(
                        contextApp, contextApp.getString(R.string.empty_data),
                        Toast.LENGTH_LONG
                    ).show()
                    return null
                }
                return output
            }
            else -> {
                return null
            }
        }
    }


    private suspend fun getResults(page: Int, isOnline: Boolean): AppState =
        withContext(ioDispatcher) {
            return@withContext interactor.getData(page, isOnline).first()
        }

    private companion object {
        const val PAGE_SIZE = 1
    }
}