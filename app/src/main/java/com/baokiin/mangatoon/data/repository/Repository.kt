package com.baokiin.mangatoon.data.repository

import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.data.model.MangaList
import com.baokiin.mangatoon.data.remote.paging.ComicPagingSource
import com.baokiin.mangatoon.data.remote.paging.DetailGenerPagingSource
import com.baokiin.mangatoon.data.remote.paging.PopularPagingSource


interface Repository{
   suspend fun getRecommended():MangaList?
   suspend fun getPopular(page:Int):MangaList?
   suspend fun getComic(comic:String,page:Int):MangaList?
   suspend fun getGenres():GenresList?
   suspend fun getDetailManga(endpoint: String):DetailManga?
   suspend fun getChapter(endpoint: String):Chapter?
   suspend fun search(search:String):MangaList?
   fun getDetailGenres(endpoint: String):DetailGenerPagingSource
   fun getPopularPaging():PopularPagingSource
   fun getComicPaging(comic: String):ComicPagingSource
}