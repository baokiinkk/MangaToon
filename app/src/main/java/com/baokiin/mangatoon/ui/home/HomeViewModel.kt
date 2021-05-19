package com.baokiin.mangatoon.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.MangaList
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(val re:Repository) :ViewModel(){
    val recommended:MutableLiveData<MangaList?> = MutableLiveData(null)
    fun getRecommended(){
        viewModelScope.launch {
            re.getRecommended()?.let {
                recommended.postValue(it)
            }
        }
    }
}