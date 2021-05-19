package com.baokiin.mangatoon.data.repository

import android.util.Log
import com.baokiin.mangatoon.data.api.ApiService
import com.baokiin.mangatoon.data.model.MangaList

class RepositoryImpl(val apiService: ApiService):Repository{
   override suspend fun getRecommended():MangaList? =
       try {
           Log.d("quocbao",apiService.getRecommended().toString())
           apiService.getRecommended()
       }
       catch (e:Exception){
           Log.d("quocbao","error")
           null
       }

}