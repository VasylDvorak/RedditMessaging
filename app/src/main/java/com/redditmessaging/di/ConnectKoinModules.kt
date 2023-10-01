package com.redditmessaging.di


import android.content.Context
import com.redditmessaging.di.koin_modules.ApiModule
import com.redditmessaging.di.koin_modules.AppModule
import com.redditmessaging.di.koin_modules.MainFragmentModule
import com.redditmessaging.model.datasource.RetrofitImplementation
import com.redditmessaging.model.loaders.repositories.FilmsRetrofitRepository
import com.redditmessaging.model.repository.OnLineRepository
import com.redditmessaging.model.repository.Repository
import com.redditmessaging.model.repository.RepositoryImplementation
import com.redditmessaging.views.MainViewModel
import com.redditmessaging.views.main_fragment.MainFragment
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin


const val mainScreenScopeName = "mainScreenScope"
const val descriptionScreenScopeName = "descriptionScreenScope"

object ConnectKoinModules {

    val application = module {
        single<Repository> { RepositoryImplementation(RetrofitImplementation()) }
        single { OnLineRepository() }
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