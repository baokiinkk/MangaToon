package com.baokiin.mangatoon.ui.mine

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MineViewModel(val local: RepositoryLocal) : ViewModel() {
    val auth = Firebase.auth
    val db = Firebase.firestore
    fun upData() {
        viewModelScope.launch {
            auth.currentUser?.let {
                val firebase = db.collection(it.uid)
                val data = local.getDataManga()

                data.forEach {
                    local.deleteManga(it)
                    firebase.document(it.title).set(it)
                }
            }
        }
    }
}