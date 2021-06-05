package com.baokiin.mangatoon.ui.detail

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.baokiin.mangatoon.adapter.ItemChapterAdapter
import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.data.repository.Repository
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.launch

class DetailViewModel(val rep: Repository, val local: RepositoryLocal) : ViewModel() {
    var data: MutableLiveData<DetailManga?> = MutableLiveData(null)
    var dataChapter: MutableLiveData<MutableList<Chapter>?> = MutableLiveData(null)
    var isLiked: Boolean = false
    val isManga: MutableLiveData<Boolean?> = MutableLiveData(null)
    var mangaLocal: Manga? = null
    var isAuth: MutableLiveData<Boolean?> = MutableLiveData(null)
    val auth = Firebase.auth
    val db = Firebase.firestore

    fun getChapFromFirestore(){
        if (auth.currentUser == null) {
            Log.d("quocbao","aaaaaaaaaaaaaa")
            dataChapter.postValue(data.value?.chapter)
        }
        else {

            auth.currentUser?.uid?.let {
                data.value?.title?.let { it1 ->
                    db.collection(it).document(it1)
                        .collection("chap")
                        .get()
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                it.documents.forEach {
                                    val gson = Gson()
                                    val jsonElement = gson.toJsonTree(it.data)
                                    val chap = gson.fromJson(jsonElement, Chapter::class.java)
                                    data.value?.chapter?.map {
                                        if (it.chapter_endpoint == chap.chapter_endpoint)
                                            it.lock = chap.lock
                                    }

                                }

                                dataChapter.postValue(data.value?.chapter)
                            }
                        }

                }
            }
        }
    }
    fun getData(endPoint: String) {
        viewModelScope.launch {
            val dataChap = rep.getDetailManga(endPoint)
            data.postValue(dataChap)
        }
    }

    fun isMangas(title: String) {
        viewModelScope.launch {
            isManga.postValue(local.isManga(title))
        }
    }

    fun onClickLottie(view: View) {
        if (auth.currentUser == null) {
            isAuth.postValue(true)
        } else {
            isLiked = !isLiked
            (view as LottieAnimationView).apply {
                if (isLiked) {
                    playAnimation()
                    setMinAndMaxProgress(0.0f, 0.5f)
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
    }

    fun select(view: View, view2: TextView, adapter: ItemChapterAdapter, rv: RecyclerView) {
        val text = (view as TextView).text.toString()[0]
        view.setTextColor(android.graphics.Color.parseColor("#53B175"))
        view2.setTextColor(android.graphics.Color.parseColor("#959393"))
        adapter.sort(text == 'P', rv)
    }
}