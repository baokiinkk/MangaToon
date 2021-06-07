package com.baokiin.mangatoon.di

import com.baokiin.mangatoon.data.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val appdaoModule= module {
    single { AppDatabase.getInstance(androidApplication()).appDao()}
}