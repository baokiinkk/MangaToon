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
            bundle.putString("endpoint",chap.chapter_endpoint)
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
                viewmodel.getChapFromFirestore(it)
                detailManga = it
            }
        })
        viewmodel.dataChapter.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }


    override fun onResume() {
        super.onResume()
        Log.d("quocbao","aaaaaa")
    }

    override fun onStop() {
        super.onStop()
        viewmodel.getChapFromFirestore(detailManga)
    }

}