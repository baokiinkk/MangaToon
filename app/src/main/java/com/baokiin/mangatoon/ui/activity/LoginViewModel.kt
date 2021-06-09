package com.baokiin.mangatoon.ui.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(val rep:RepositoryLocal) :ViewModel(){
    val db = Firebase.firestore
    fun getDataFromFirestore(auth:FirebaseUser?){
        auth?.let {
            db.collection(it.uid).get()
                .addOnSuccessListener { doc ->
                    GlobalScope.launch(Dispatchers.IO) {
                        doc.documents.forEach {
                            if(it.get("coin") == null) {
                                val gson = Gson()
                                val jsonElement = gson.toJsonTree(it.data)
                                val data = gson.fromJson(jsonElement, Manga::class.java)
                                rep.insertManga(data)
                            }
                        }
                    }

                }
        }
    }

    fun createAuth(auth: FirebaseUser?){
        auth?.let { user->
            db.collection(user.uid).document("Coin").get()
                .addOnSuccessListener {
                    if(it.get("coin") == null){
                        db.collection(user.uid).document("Coin").set(mapOf("coin" to 50))
                    }
                }
        }
    }

}