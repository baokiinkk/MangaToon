package com.baokiin.mangatoon.ui.chap

import android.os.Bundle
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentChapterBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemChapterAdapter
import com.baokiin.mangatoon.ui.chapterdetail.DetailChapterFragment
import com.baokiin.mangatoon.ui.chapterdetail.BacktoChap
import com.baokiin.mangatoon.ui.detail.DetailViewModel
import com.baokiin.mangatoon.utils.Utils.DETAILMANGA
import com.baokiin.mangatoon.utils.Utils.ENDPOINT
import com.baokiin.mangatoon.utils.Utils.MANGA
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChapterFragment :
    BaseFragment<FragmentChapterBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_chapter
    }
    val viewmodel:DetailViewModel by sharedViewModel()
    override fun onCreateViews() {
        baseBinding.viewmodel =viewmodel
        val adapter = ItemChapterAdapter{ chap->
            val bundle = Bundle()
            val index = chap.chapter_title?.split(" ")?.get(0)?.toInt()
            if (index != null) {
                bundle.putInt(ENDPOINT,index)
            }
            bundle.putSerializable(DETAILMANGA,viewmodel.data.value)
            bundle.putSerializable(MANGA,viewmodel.mangaLocal)
            val fragment = DetailChapterFragment()
            fragment.resiveOnBackFromDetailChap(object :BacktoChap{
                override fun onClickBack() {
                    viewmodel.getChapFromFirestore()
                }

            })
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.containerFragment,fragment)
                .addToBackStack(ChapterFragment()::class.java.simpleName)
                .commit()
        }
        baseBinding.adapter = adapter
        viewmodel.getChapFromFirestore()
        viewmodel.dataChapter.observe(viewLifecycleOwner, Observer {
            it?.let {
                val tmp = it
                for(i in 0 until tmp.size){
                    tmp[i].chapter_title = "${tmp.size-i-1} "+tmp[i].chapter_title
                }
                adapter.submitList(tmp)
                adapter.notifyDataSetChanged()
            }
        })
    }

}