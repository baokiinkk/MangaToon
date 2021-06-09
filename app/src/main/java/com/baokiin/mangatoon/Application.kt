package com.baokiin.mangatoon

import android.app.Application
import com.baokiin.mangatoon.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(listOf(
                RepositoryDi,
                blankViewmodelDi,
                apiModule,
                retrofitModule,
                appdaoModule
            ))
        }
    }

}