package com.redditmessaging.di


import android.content.Context
import androidx.room.Room
import com.redditmessaging.di.koin_modules.ApiModule
import com.redditmessaging.di.koin_modules.AppModule
import com.redditmessaging.di.koin_modules.MainFragmentModule
import com.redditmessaging.model.datasource.RetrofitImplementation
import com.redditmessaging.model.loaders.repositories.FilmsRetrofitRepository
import com.redditmessaging.model.repository.Repository
import com.redditmessaging.model.repository.RepositoryImplementation
import com.redditmessaging.model.repository.RepositoryImplementationLocal
import com.redditmessaging.model.repository.RepositoryLocal
import com.redditmessaging.model.room.MessageDataBase
import com.redditmessaging.model.room.RoomDataBaseImplementation
import com.redditmessaging.utils.OnlineRepository
import com.redditmessaging.views.MainViewModel
import com.redditmessaging.views.main_fragment.MainFragment
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

const val dataBaseName = "database"
const val mainScreenScopeName = "mainScreenScope"

object ConnectKoinModules {

    val application = module {
        single {
            Room.databaseBuilder(get(), MessageDataBase::class.java, dataBaseName).build()
        }
        single { get<MessageDataBase>().messageDao() }
        single<Repository> { RepositoryImplementation(RetrofitImplementation()) }
        single<RepositoryLocal> {
            RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
        }
         single <OnlineRepository> { OnlineRepository() }

    }



    val mainScreen = module {
        scope(named<MainFragment>()) {
            viewModel { MainViewModel(FilmsRetrofitRepository(Dispatchers.IO)) }
        }
    }

    val mainScreenScope by lazy {
        getKoin()
            .getOrCreateScope(mainScreenScopeName, named<MainFragment>())
    }


    val apiModule = module {
        factory { ApiModule().getService() }
    }

    val appModule = module {
        scope(named<Context>()) {
            scoped { AppModule().applicationContext(context = androidApplication()) }
        }
    }


    val mainFragmentModule = module {
        single { MainFragmentModule().mainFragment() }

    }
}