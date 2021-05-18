package com.baokiin.mangatoon.data.repository

import android.util.Log
import com.baokiin.mangatoon.data.api.ApiService
import com.baokiin.mangatoon.data.model.Fruit

class RepositoryImpl(val apiService: ApiService):Repository{
   override suspend fun getData(get: (Fruit) -> Unit) {
       try {
           Log.d("quocbao",apiService.getBooks().toString())
           get(apiService.getBooks())
       }
       catch (e:Exception){
           Log.d("quocbao","error")

       }
    }
}