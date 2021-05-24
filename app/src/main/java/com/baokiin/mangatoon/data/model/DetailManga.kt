package com.baokiin.mangatoon.data.model

data class DetailManga(
    val title: String?,
    val type: String?,
    val thumb: String?,
    val author: String?,
    val status:String?,
    val synopsis:String?,
    val chapter:MutableList<Chapter>,
    val genre_list: MutableList<Genre>
)
data class Chapter(val chapter_endpoint:String?,val chapter_title:String?,val chapter_image:MutableList<ChapImage>?)
data class ChapImage(val chapter_image_link:String?)



