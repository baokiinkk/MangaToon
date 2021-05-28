package com.baokiin.mangatoon.ui.detail

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.compose.ui.res.integerResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemChapterAdapter
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
    private var isLiked: Boolean = false
    fun onClickLottie(view: View) {
        isLiked = !isLiked
        (view as LottieAnimationView).apply {
            if (isLiked) {
                playAnimation()
                setMinAndMaxProgress(0.0f,0.5f)
            } else {
                cancelAnimation()
                progress = 0.0f
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