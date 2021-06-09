package com.baokiin.mangatoon.ui.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MineViewModel(val local: RepositoryLocal) : ViewModel() {
    var auth = MutableLiveData(Firebase.auth)
    private val db = Firebase.firestore
    var coin: MutableLiveData<Long> = MutableLiveData(0)
    fun upData() {
        viewModelScope.launch {
            auth.value?.currentUser?.let { user->
                val firebase = db.collection(user.uid)
                val data = local.getDataManga()

                data.forEach {
                    local.deleteManga(it)
                    firebase.document(it.title).set(it)
                }
            }
        }
    }

    fun getCoinUser(user: FirebaseUser) {
            db.collection(user.uid).document("Coin").get().addOnSuccessListener { doc ->
                doc.get("coin")?.let {
                    coin.postValue(it as Long)
                }
            }
    }
}