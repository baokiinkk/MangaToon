package com.baokiin.mangatoon.data.api

import com.baokiin.mangatoon.data.model.MangaList
import retrofit2.http.GET

interface ApiService {
    @GET("recommended")
    suspend fun getRecommended(): MangaList
}