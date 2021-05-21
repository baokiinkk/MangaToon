package com.baokiin.mangatoon.ui.genre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class GenreViewModel(val rep:Repository):ViewModel() {
    val genres: MutableLiveData<GenresList?> = MutableLiveData(null)
    fun getGenres(){
        viewModelScope.launch {
            rep.getGenres()?.let {
                genres.postValue(it)
            }
        }
    }
}