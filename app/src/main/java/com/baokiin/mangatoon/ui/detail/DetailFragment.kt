package com.baokiin.mangatoon.ui.detail

import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentDetailBinding
import com.baokiin.mangatoon.ui.base.BaseFragment
import com.baokiin.mangatoon.ui.adapter.ViewPageAdapter
import com.baokiin.mangatoon.ui.chap.ChapterFragment
import com.baokiin.mangatoon.ui.description.DescriptionFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DetailFragment : BaseFragment<FragmentDetailBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail
    }
    private val viewModel:DetailViewModel by sharedViewModel()
    override fun onCreateViews() {

        val endPoint:String = arguments?.get("endPoint").toString()
        val adapterViewPager = ViewPageAdapter(mutableListOf(DescriptionFragment(),ChapterFragment()), requireActivity())
        viewModel.getData(endPoint)
        baseBinding.apply {
            viewmodel = viewModel
            adapter = adapterViewPager
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

}