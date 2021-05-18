package com.baokiin.mangatoon

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.baokiin.mangatoon.di.RepositoryDi
import com.baokiin.mangatoon.di.apiModule
import com.baokiin.mangatoon.di.blankViewmodelDi
import com.baokiin.mangatoon.di.retrofitModule

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(listOf(
                RepositoryDi,
                blankViewmodelDi,
                apiModule,
                retrofitModule
            ))
        }
    }

}