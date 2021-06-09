package com.baokiin.mangatoon.di

import com.baokiin.mangatoon.data.repository.Repository
import com.baokiin.mangatoon.data.repository.RepositoryImpl
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import com.baokiin.mangatoon.data.repository.RepositoryLocalImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val RepositoryDi: Module = module {
    single<Repository> { RepositoryImpl(get()) }
    single<RepositoryLocal> { RepositoryLocalImpl(get()) }
}
