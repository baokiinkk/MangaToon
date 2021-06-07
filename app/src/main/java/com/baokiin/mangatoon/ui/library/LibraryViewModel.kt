package com.baokiin.mangatoon.ui.library

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import kotlinx.coroutines.launch

class LibraryViewModel(val local:RepositoryLocal) :ViewModel(){
    val data: MutableLiveData<MutableList<Manga>?> = MutableLiveData(null)
    fun getData(){
        viewModelScope.launch {
            data.postValue(local.getDataMangaFavourite())
        }
    }
}