package com.baokiin.mangatoon.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentDetailBinding
import com.baokiin.mangatoon.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail
    }
    val viewModel:DetailViewModel by viewModel()
    override fun onCreateViews() {
        baseBinding.viewmodel = viewModel
        val endPoint:String = arguments?.get("endPoint").toString()
        viewModel.getData(endPoint)
    }

}