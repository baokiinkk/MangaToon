package com.baokiin.mangatoon.data.repository

import com.baokiin.mangatoon.data.model.Fruit


interface Repository{
   suspend fun getData(get: (Fruit) -> Unit)
}