package com.baokiin.mangatoon.ui.chapterdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.ChapImage
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class DetailChapterViewModel(val rep:Repository) :ViewModel(){
    val data:MutableLiveData<MutableList<ChapImage>?> = MutableLiveData(null)

    fun getData(endpoint:String){
        viewModelScope.launch {
            rep.getChapter(endpoint)?.let {
                data.postValue(it.chapter_image)
            }
        }
    }
}