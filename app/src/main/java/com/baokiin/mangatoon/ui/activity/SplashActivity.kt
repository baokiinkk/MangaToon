package com.baokiin.mangatoon.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Start home activity
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        // close splash activity
        finish()
    }
}