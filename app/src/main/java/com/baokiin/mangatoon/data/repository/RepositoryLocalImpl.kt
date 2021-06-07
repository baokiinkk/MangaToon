package com.baokiin.mangatoon.data.repository

import com.baokiin.mangatoon.data.local.AppDao
import com.baokiin.mangatoon.data.model.Manga

class RepositoryLocalImpl(val dao: AppDao) : RepositoryLocal {
    override suspend fun getDataManga(): MutableList<Manga> =
        dao.getDataManga()

    override suspend fun insertManga(manga: Manga): Boolean =
        try {
            dao.addManga(manga)
            true
        } catch (e: Exception) {
            false
        }


    override suspend fun deleteManga(manga: Manga): Boolean =
        try {
            dao.deleteResultLocal(manga)
            true
        } catch (e: Exception) {
            false
        }

    override suspend fun isManga(title: String): Boolean =
        try {
            if (dao.isManga(title) != null)
                true
            else
                false
        } catch (e: Exception) {
            false
        }

}