package com.baokiin.mangatoon.ui.detailgener

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.data.model.MangaList
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class DetailGenerViewModel(val rep: Repository) : ViewModel() {
    var data: LiveData<PagingData<Manga>> = MutableLiveData()
    fun getData(endPoint: String) {
        data = Pager(
                 PagingConfig(20, 1, true, 1)
            ){
                rep.getDetailGenres(endPoint)
            }.liveData
    }
}