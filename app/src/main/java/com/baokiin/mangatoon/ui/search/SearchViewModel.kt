package com.baokiin.mangatoon.ui.search

import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.MangaList
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class SearchViewModel(val rep:Repository) :ViewModel(){
    val data: MutableLiveData<MangaList?> = MutableLiveData(null)
    fun search(search:String){
        viewModelScope.launch {
            data.postValue(rep.search(search))
        }
    }
}