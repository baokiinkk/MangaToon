package com.baokiin.mangatoon.data.repository

import android.util.Log
import com.baokiin.mangatoon.data.api.ApiService
import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.data.model.MangaList

class RepositoryImpl(val apiService: ApiService):Repository{
   override suspend fun getRecommended():MangaList? =
       try {
           apiService.getRecommended()
       }
       catch (e:Exception){
           Log.d("quocbao","error")
           null
       }

    override suspend fun getPopular(page:Int): MangaList?=
        try{
            apiService.getPopular(page)
        }
        catch (e:Exception){
            Log.d("quocbao","errorPopular")
            null
        }

    override suspend fun getComic(comic:String,page: Int): MangaList? =
        try{
            apiService.getComic(comic,page)
        }
        catch (e:Exception){
            Log.d("quocbao","errorPopular")
            null
        }

    override suspend fun getGenres(): GenresList? =
        try{
            apiService.getGenres()
        }
        catch (e:Exception){
            Log.d("quocbao","errorPopular")
            null
        }

}