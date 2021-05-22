package com.baokiin.mangatoon.data.model

import java.io.Serializable

data class GenresList(val list_genre:MutableList<Genre>)
data class Genre(val genre_name:String,val endpoint:String?) :Serializable