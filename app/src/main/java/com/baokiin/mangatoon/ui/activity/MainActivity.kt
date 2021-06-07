package com.baokiin.mangatoon.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.baokiin.mangatoon.R
import androidx.databinding.DataBindingUtil
import com.baokiin.mangatoon.databinding.ActivityMainBinding
import com.baokiin.mangatoon.adapter.ViewPageAdapter
import com.baokiin.mangatoon.ui.genre.GenreFragment
import com.baokiin.mangatoon.ui.home.HomeFragment
import com.baokiin.mangatoon.ui.library.LibraryFragment
import com.baokiin.mangatoon.ui.mine.MineFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val baseBinding:ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        baseBinding.apply {
            lifecycleOwner = this@MainActivity
            adapter = ViewPageAdapter(
                mutableListOf(HomeFragment(),GenreFragment(),LibraryFragment(),MineFragment()),this@MainActivity)
        }
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


    }
}