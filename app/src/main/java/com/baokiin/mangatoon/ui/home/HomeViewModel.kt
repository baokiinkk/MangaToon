package com.baokiin.mangatoon.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.data.model.MangaList
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(val repository:Repository) :ViewModel(){
    val recommended:MutableLiveData<MangaList?> = MutableLiveData(null)
    val popular:MutableLiveData<MangaList?> = MutableLiveData(null)
    val genres:MutableLiveData<GenresList?> = MutableLiveData(null)
    val manHua:MutableLiveData<MangaList?> = MutableLiveData(null)
    val manhwa :MutableLiveData<MangaList?> = MutableLiveData(null)

    fun getRecommended(){
        viewModelScope.launch {
            repository.getRecommended()?.let {
                recommended.postValue(it)
            }
        }
    }
    fun getPopular(page:Int){
        viewModelScope.launch {
            repository.getPopular(page)?.let {
                popular.postValue(it)
            }
        }
    }

    fun getManhua(page:Int){
        viewModelScope.launch {
            repository.getComic("manhua",page)?.let {
                manHua.postValue(it)
            }
        }
    }
    fun getManhwa(page:Int){
        viewModelScope.launch {
            repository.getComic("manhwa",page)?.let {
                manhwa.postValue(it)
            }
        }
    }

    fun getGenres(){
        viewModelScope.launch {
            repository.getGenres()?.let {
                genres.postValue(it)
            }
        }
    }
}