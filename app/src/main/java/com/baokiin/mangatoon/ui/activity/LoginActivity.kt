package com.baokiin.mangatoon.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.local.AppDao
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.data.model.UserSNS
import com.baokiin.mangatoon.data.repository.RepositoryLocal
import com.baokiin.mangatoon.data.repository.RepositoryLocalImpl
import com.baokiin.mangatoon.sns.SNSLoginActivity
import com.baokiin.mangatoon.utils.Utils.USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : SNSLoginActivity() {
    private lateinit var auth: FirebaseAuth
    val viewModel:LoginViewModel by viewModel()
    override fun onResume() {
        super.onResume()
        auth.currentUser?.let {
            val intent = Intent(this, MainActivity::class.java)
                .apply {
                    putExtra(USER, it)
                }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        init()

    }

    override fun onSNSUserResult(user: UserSNS) {
        updateUI(user)
    }

    //----------------------------
    private fun init() {
        //Instance firebase auth
        auth = Firebase.auth

        button_loginGg.setOnClickListener {
            signInGoogle()
        }
        button_loginFb.setOnClickListener {
            signInFb()
        }
        btnClose.setOnClickListener {
            finish()
        }

    }

//

    private fun updateUI(user: UserSNS?) {
       viewModel.getDataFromFirestore(auth.currentUser)
        finish()

    }
}