package com.baokiin.mangatoon.ui.chap

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentChapterBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemChapterAdapter
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.ui.chapterdetail.DetailChapterFragment
import com.baokiin.mangatoon.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChapterFragment :
    BaseFragment<FragmentChapterBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_chapter
    }
    val viewmodel:DetailViewModel by sharedViewModel()
    lateinit var detailManga: DetailManga
    override fun onCreateViews() {
        baseBinding.viewmodel =viewmodel
        val adapter = ItemChapterAdapter{ chap, i ->
            viewmodel.unlock(chap)
            val bundle = Bundle()
            val index = chap.chapter_title?.split(" ")?.get(0)?.toInt()
            if (index != null) {
                bundle.putInt("endpoint",index)
            }
            bundle.putSerializable("detailManga",viewmodel.data.value)
            val fragment = DetailChapterFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.containerFragment,fragment)
                .addToBackStack(ChapterFragment()::class.java.simpleName)
                .commit()
        }
        baseBinding.adapter = adapter
        viewmodel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("quocbao","aaaaaaaaaaaaaaaaaaaa")
                viewmodel.getChapFromFirestore(it)
                detailManga = it
            }
        })
        viewmodel.dataChapter.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("quocbao","bbbbbbbbbbbbbbbbbbbb")
                for(i in 0..it.size-1){
                    it[i].chapter_title = "${it.size-i-1} "+it[i].chapter_title
                }
                adapter.submitList(it)
            }
        })
    }


    override fun onResume() {
        super.onResume()
        viewmodel.getChapFromFirestore(detailManga)
    }



}