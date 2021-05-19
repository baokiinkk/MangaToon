package com.baokiin.mangatoon.data.model


data class MangaList(val manga_list:MutableList<Manga>,val message:String)
data class Manga(val title:String?,val type:String?,val thumb:String?,val endpoint:String?,val upload_on:String?,val updated_on:String?)



