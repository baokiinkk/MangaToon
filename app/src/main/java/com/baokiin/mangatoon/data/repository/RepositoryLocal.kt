package com.baokiin.mangatoon.data.repository

import com.baokiin.mangatoon.data.model.*
import com.baokiin.mangatoon.data.remote.paging.ComicPagingSource
import com.baokiin.mangatoon.data.remote.paging.DetailGenerPagingSource
import com.baokiin.mangatoon.data.remote.paging.PopularPagingSource


interface RepositoryLocal{
   suspend fun getDataManga():MutableList<Manga>
   suspend fun insertManga(manga:Manga):Boolean
   suspend fun deleteManga(manga:Manga):Boolean
   suspend fun isManga(title:String):Boolean
}