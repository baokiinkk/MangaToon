package com.baokiin.mangatoon.data.model

data class GenresList(val list_genre:MutableList<Genre>)
data class Genre(val genre_name:String?,val endpoint:String?)