package com.baokiin.mangatoon.data.repository

import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.data.model.MangaList


interface Repository{
   suspend fun getRecommended():MangaList?
   suspend fun getPopular(page:Int):MangaList?
   suspend fun getComic(comic:String,page:Int):MangaList?
   suspend fun getGenres():GenresList?
}