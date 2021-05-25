package com.baokiin.mangatoon.ui.chapterdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentDetailChapterBinding
import com.baokiin.mangatoon.ui.BaseFragment
import com.baokiin.mangatoon.ui.adapter.ItemDetailChapterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailChapterFragment :BaseFragment<FragmentDetailChapterBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_chapter
    }

    val viewModel:DetailChapterViewModel by viewModel()
    override fun onCreateViews() {
        baseBinding.viewmodel = viewModel
        val endpoint:String = arguments?.get("endpoint").toString()
        var clickItem = false
        val adapter = ItemDetailChapterAdapter{chapImage, i ->

            baseBinding.constraintLayout2.apply {
                if(!clickItem){
                    transitionToEnd()
                    clickItem = true
                }
                else{
                    transitionToStart()
                    clickItem = false
                }

            }
        }
        baseBinding.adapter = adapter
        viewModel.getData(endpoint)
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}