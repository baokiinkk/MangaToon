package com.baokiin.mangatoon.data.local

import androidx.room.*
import com.baokiin.mangatoon.data.model.Manga

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addManga(manga: Manga)

    @Delete
    suspend fun deleteResultLocal(vararg manga: Manga)

    // querry mangalist
    @Query("select * from Manga")
    suspend fun getDataManga():MutableList<Manga>
    @Query("select * from Manga where Manga.title=:id and Manga.favourite = 1")
    suspend fun isMangaFavourite(id:String):Manga?
    @Update
    suspend fun updateManga(manga: Manga)


}