package com.baokiin.mangatoon.data.repository

import com.baokiin.mangatoon.data.model.MangaList


interface Repository{
   suspend fun getRecommended():MangaList?
}