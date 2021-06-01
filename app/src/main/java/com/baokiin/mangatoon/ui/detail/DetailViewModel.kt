package com.baokiin.mangatoon.ui.detail

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.baokiin.mangatoon.adapter.ItemChapterAdapter
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.data.repository.Repository
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import kotlinx.coroutines.launch

class DetailViewModel(val rep:Repository,val local:RepositoryLocal) :ViewModel(){
    var data: MutableLiveData<DetailManga?> = MutableLiveData(null)
    var isLiked: Boolean = false
    val isManga:MutableLiveData<Boolean?> = MutableLiveData(null)
    var mangaLocal:Manga? = null
    fun getData(endPoint:String){
        viewModelScope.launch {
            data.postValue(rep.getDetailManga(endPoint))
        }
    }
    fun isMangas(title:String){
        viewModelScope.launch {
            isManga.postValue(local.isManga(title))
        }
    }

    fun onClickLottie(view: View) {
        isLiked = !isLiked
        (view as LottieAnimationView).apply {
            if (isLiked) {
                playAnimation()
                setMinAndMaxProgress(0.0f,0.5f)
                viewModelScope.launch {
                    mangaLocal?.let {
                        local.insertManga(it)
                    }
                }
            } else {
                cancelAnimation()
                progress = 0.0f
                viewModelScope.launch {
                    mangaLocal?.let {
                        local.deleteManga(it)
                    }
                }
            }
        }
    }
    fun select(view: View, view2: TextView, adapter: ItemChapterAdapter,rv:RecyclerView){
        val text = (view as TextView).text.toString()[0]
        view.setTextColor(android.graphics.Color.parseColor("#53B175"))
        view2.setTextColor(android.graphics.Color.parseColor("#959393"))
        adapter.sort(text == 'P',rv)
    }
}