package com.baokiin.mangatoon.ui.chapterdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.ChapImage
import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.repository.Repository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class DetailChapterViewModel(val rep: Repository) : ViewModel() {
    val data: MutableLiveData<Chapter?> = MutableLiveData(null)
    val auth = Firebase.auth
    val db = Firebase.firestore
    fun getData(endpoint: String) {
        viewModelScope.launch {
            rep.getChapter(endpoint)?.let {
                data.postValue(it)
            }
        }
    }

    fun unlock(chap: Chapter, title: String) {
        auth.currentUser?.uid?.let {
            chap.lock = true
            chap.chapter_endpoint?.let { it2 ->
                db.collection(it).document(title)
                    .collection("chap")
                    .document(it2)
                    .set(chap)
            }
        }
    }
}