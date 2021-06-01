package com.baokiin.mangatoon.data.remote.api

import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.model.DetailManga
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
    @GET("manga/detail/{endpoint}")
    suspend fun getDetailManga(@Path("endpoint")endPoint: String): DetailManga

    @GET("chapter/{chapterEndpoint}")
    suspend fun getChapterImage(@Path("chapterEndpoint")endPoint: String): Chapter

    @GET("search/{query}")
    suspend fun search(@Path("query") query:String):MangaList
}