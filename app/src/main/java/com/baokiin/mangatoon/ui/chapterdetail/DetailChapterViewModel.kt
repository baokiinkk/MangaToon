package com.baokiin.mangatoon.ui.chapterdetail

import android.hardware.usb.UsbEndpoint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.ChapImage
import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.repository.Repository
import kotlinx.coroutines.launch

class DetailChapterViewModel(val rep:Repository) :ViewModel(){
    val data:MutableLiveData<Chapter?> = MutableLiveData(null)
    var title = ""
    fun getData(endpoint:String){
        viewModelScope.launch {
            rep.getChapter(endpoint)?.let {
                data.postValue(it)
            }
        }
    }
}