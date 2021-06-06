package com.baokiin.mangatoon.data.repository

import android.util.Log
import com.baokiin.mangatoon.data.remote.api.ApiService
import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.data.model.MangaList
import com.baokiin.mangatoon.data.remote.paging.ComicPagingSource
import com.baokiin.mangatoon.data.remote.paging.DetailGenerPagingSource
import com.baokiin.mangatoon.data.remote.paging.PopularPagingSource

class RepositoryImpl(val apiService: ApiService) : Repository {
    override suspend fun getRecommended(): MangaList? =
        try {
            apiService.getRecommended()
        } catch (e: Exception) {
            Log.d("quocbao", "error")
            null
        }

    override suspend fun getPopular(page: Int): MangaList? =
        try {
            apiService.getPopular(page)
        } catch (e: Exception) {
            Log.d("quocbao", "errorPopular")
            null
        }

    override suspend fun getComic(comic: String, page: Int): MangaList? =
        try {
            apiService.getComic(comic, page)
        } catch (e: Exception) {
            Log.d("quocbao", "errorPopular")
            null
        }

    override suspend fun getGenres(): GenresList? =
        try {
            apiService.getGenres()
        } catch (e: Exception) {
            Log.d("quocbao", "errorPopular")
            null
        }

    override suspend fun getDetailManga(endpoint: String): DetailManga? =
        try {
            apiService.getDetailManga(endpoint)
        } catch (e: Exception) {
            Log.d("quocbao", "errorPopular")
            null
        }

    override suspend fun getChapter(endpoint: String): Chapter? =
        try {
            apiService.getChapterImage(endpoint)
        } catch (e: java.lang.Exception) {
            null
        }

    override suspend fun search(search: String): MangaList? = try {
        apiService.search(search)
    } catch (e: java.lang.Exception) {
        null
    }


    override fun getDetailGenres(endpoint: String): DetailGenerPagingSource =
        DetailGenerPagingSource(apiService, endpoint)

    override fun getPopularPaging(): PopularPagingSource =
        PopularPagingSource(apiService)

    override fun getComicPaging(comic: String): ComicPagingSource =
        ComicPagingSource(apiService, comic)
}