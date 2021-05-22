package com.baokiin.mangatoon.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class DetailViewModel(val rep:Repository) :ViewModel(){
    var data: MutableLiveData<DetailManga?> = MutableLiveData(null)

    fun getData(endPoint:String){
        viewModelScope.launch {
            data.postValue(rep.getDetailManga(endPoint))
        }
    }
}