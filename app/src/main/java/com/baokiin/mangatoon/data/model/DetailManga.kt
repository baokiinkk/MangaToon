package com.baokiin.mangatoon.data.model

import java.io.Serializable

data class DetailManga(
    val title: String?,
    val type: String?,
    val thumb: String?,
    val author: String?,
    val status:String?,
    val synopsis:String?,
    val chapter:MutableList<Chapter>,
    val genre_list: MutableList<Genre>
) : Serializable

data class Chapter(
    var chapter_endpoint:String?, var chapter_title:String?, val chapter_image:MutableList<ChapImage>?,
    var lock:Boolean = true)
data class ChapImage(val chapter_image_link:String?)



