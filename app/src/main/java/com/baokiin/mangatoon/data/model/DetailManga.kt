package com.baokiin.mangatoon.data.model

data class DetailManga(
    val title: String?,
    val type: String?,
    val thumb: String?,
    val author: String?,
    val synopsis:String?,
    val chapter:MutableList<Chapter>,
    val genre_list: MutableList<Genre>
)
data class Chapter(val chapter_endpoint:String?)



