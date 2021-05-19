package com.baokiin.mangatoon.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.data.model.MangaList
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(val re:Repository) :ViewModel(){
    val recommended:MutableLiveData<MangaList?> = MutableLiveData(null)
    val popular:MutableLiveData<MangaList?> = MutableLiveData(null)
    val genres:MutableLiveData<GenresList?> = MutableLiveData(null)
    val manHua:MutableLiveData<MangaList?> = MutableLiveData(null)
    fun getRecommended(){
        viewModelScope.launch {
            re.getRecommended()?.let {
                recommended.postValue(it)
            }
        }
    }
    fun getPopular(page:Int){
        viewModelScope.launch {
            re.getPopular(page)?.let {
                popular.postValue(it)
            }
        }
    }

    fun getManhua(page:Int){
        viewModelScope.launch {
            re.getManhua(page)?.let {
                manHua.postValue(it)
            }
        }
    }

    fun getGenres(){
        viewModelScope.launch {
            re.getGenres()?.let {
                genres.postValue(it)
            }
        }
    }
}