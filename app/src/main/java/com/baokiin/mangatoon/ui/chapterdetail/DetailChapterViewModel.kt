package com.baokiin.mangatoon.ui.chapterdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.data.repository.Repository
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class DetailChapterViewModel(private val rep: Repository, val local: RepositoryLocal) : ViewModel() {
    val data: MutableLiveData<Chapter?> = MutableLiveData(null)
    val isUnlock: MutableLiveData<Boolean?> = MutableLiveData(null)
    val auth = Firebase.auth
    private val db = Firebase.firestore
    fun getData(endpoint: String) {
        viewModelScope.launch {
            rep.getChapter(endpoint)?.let {
                data.postValue(it)
            }
        }
    }

    fun insertRecents(manga: Manga?) {
        viewModelScope.launch {

            manga?.let {
                it.recents = true
                if (local.isMangaFavourite(it.title)) {
                    it.favourite = true
                    local.updateManga(it)
                } else
                    local.insertManga(it)
            }
        }
    }

    fun unlock(chap: Chapter?, title: String) {
        auth.currentUser?.uid?.let { idUser->
            val collectionFireBase = db.collection(idUser)
                collectionFireBase.document("Coin").get()
                .addOnSuccessListener {
                    val coin = it["coin"] as Long
                    if(coin<20L)
                        isUnlock.postValue(false)
                    else{
                        collectionFireBase.document("Coin").set(mapOf("coin" to coin-20L))
                            .addOnSuccessListener {
                                chap?.apply {
                                    lock = true
                                    chapter_endpoint?.let { endpoint ->
                                        db.collection(idUser).document(title)
                                            .collection("chap")
                                            .document(endpoint)
                                            .set(this)
                                    }
                                }
                            }
                    }
                }
        }
    }

    fun isLockFromFireBase(endpoint: String?, title: String) {
        auth.currentUser?.uid?.let {
            endpoint?.let { point ->
                db.collection(it).document(title)
                    .collection("chap")
                    .document(point)
                    .get()
                    .addOnSuccessListener { doc ->
                        if (doc.get("lock") == null)
                            isUnlock.postValue(true)
                    }
            }
        }
    }
}