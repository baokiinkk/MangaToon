package com.baokiin.mangatoon.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentDetailBinding
import com.baokiin.mangatoon.ui.BaseFragment
import com.baokiin.mangatoon.ui.adapter.ViewPageAdapter
import com.baokiin.mangatoon.ui.adapter.ViewPageDetailAdapter
import com.baokiin.mangatoon.ui.chap.ChapterFragment
import com.baokiin.mangatoon.ui.description.DescriptionFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DetailFragment : BaseFragment<FragmentDetailBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail
    }
    val viewModel:DetailViewModel by sharedViewModel()
    override fun onCreateViews() {
        baseBinding.viewmodel = viewModel
        val endPoint:String = arguments?.get("endPoint").toString()
        Log.d("quocbao",endPoint)
        viewModel.getData(endPoint)
        val adapter = ViewPageAdapter(mutableListOf(DescriptionFragment(),ChapterFragment()), requireActivity())
        baseBinding.adapter = adapter
    }

}