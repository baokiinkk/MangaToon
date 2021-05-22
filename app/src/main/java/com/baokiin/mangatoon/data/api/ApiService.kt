package com.baokiin.mangatoon.data.api

import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.data.model.MangaList
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("recommended")
    suspend fun getRecommended(): MangaList

    @GET("manga/popular/{page}")
    suspend fun getPopular(@Path("page") page: Int): MangaList

    @GET("{comic}/{page}")
    suspend fun getComic(@Path("comic") comic: String,
                         @Path("page") page: Int
                        ): MangaList

    @GET("genres")
    suspend fun getGenres(): GenresList

    @GET("genres/{endpoint}/{pagenumber}")
    suspend fun getDetailGenres(@Path("endpoint") endPoint: String,
                                @Path("pagenumber") pagenumber:Int
                                ):MangaList
}