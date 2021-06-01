package com.baokiin.mangatoon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class MangaList(val manga_list:MutableList<Manga>,val message:String)

@Entity
data class Manga(@PrimaryKey val title:String, val type:String?, val thumb:String?, val endpoint:String?, val upload_on:String?, val updated_on:String?):Serializable



