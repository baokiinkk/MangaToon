package com.baokiin.mangatoon.ui.chapterdetail

import android.view.View
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.databinding.FragmentDetailChapterBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemDetailChapterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailChapterFragment :
    BaseFragment<FragmentDetailChapterBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_chapter
    }
    var indexManga =-1
    val viewModel:DetailChapterViewModel by viewModel()
    lateinit var onBack:backtoChap
    override fun onCreateViews() {

        var endpointIndex = arguments?.getInt("endpoint")

        val detailManga = arguments?.getSerializable("detailManga") as DetailManga
        var clickItem = false
        val adapters = ItemDetailChapterAdapter{chapImage, i ->

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
        if (endpointIndex != null) {
            endpointIndex = detailManga.chapter.size -1 - endpointIndex
            detailManga.chapter.get(endpointIndex).chapter_endpoint?.let { viewModel.getData(it) }
            indexManga = endpointIndex
            if(indexManga == 0)
                baseBinding.btnNextChap.visibility = View.GONE
            if(indexManga == detailManga.chapter.size-1)
                baseBinding.btnBackChap.visibility = View.GONE
        }
        baseBinding.apply {
            viewmodel = viewModel
            adapter = adapters
            btnBack.setOnClickListener {
                onBack.onClickBack()
                requireActivity().onBackPressed()
            }
            btnBackChap.setOnClickListener {
                changeChap(detailManga,false,it)
                btnNextChap.visibility = View.VISIBLE
            }
            btnNextChap.setOnClickListener {
                changeChap(detailManga,true,it)
                btnBackChap.visibility = View.VISIBLE
            }
        }
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.chapter_endpoint =
                    it.chapter_endpoint?.length?.let { it1 ->
                        it.chapter_endpoint?.substring(0,
                            it1-1
                        )
                    }
                detailManga.title?.let { it1 ->

                    viewModel.unlock(it, it1)
                }
                adapters.submitList(it.chapter_image)
            }
        })
    }
    fun resiveOnBackFromDetailChap(click:backtoChap){
        onBack = click
    }
    fun changeChap(detailManga:DetailManga,boolean: Boolean,view: View){
        if(boolean){
            indexManga--
            if(indexManga == 0)
                view.visibility = View.GONE
        }
        else {
            indexManga++
            if(indexManga == detailManga.chapter.size-1)
                view.visibility = View.GONE
        }
        detailManga.chapter.get(indexManga).chapter_endpoint?.let { viewModel.getData(it) }
    }
}

interface backtoChap{
    fun onClickBack()
}