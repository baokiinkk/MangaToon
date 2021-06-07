package com.baokiin.mangatoon.ui.description


import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentDescriptionBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemGenreDescriptionAdapter
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.ui.chap.ChapterFragment
import com.baokiin.mangatoon.ui.chapterdetail.DetailChapterFragment
import com.baokiin.mangatoon.ui.detail.DetailFragment
import com.baokiin.mangatoon.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DescriptionFragment : BaseFragment<FragmentDescriptionBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_description
    }

    val viewModel: DetailViewModel by sharedViewModel()

    override fun onCreateViews() {
        val adapterDes = ItemGenreDescriptionAdapter { genre, i ->

        }
        baseBinding.apply {
            viewmodel = viewModel
            adapter = adapterDes

        }
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterDes.submitList(it.genre_list)


                baseBinding.apply {
                    btnRead.setOnClickListener { v ->
                        val bundle = Bundle()
                        bundle.putString(
                            "endpoint",
                            it.chapter[it.chapter.size - 1].chapter_endpoint
                        )
                        val fragment = DetailChapterFragment()
                        fragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.containerFragment, fragment)
                            .addToBackStack(DetailFragment::class.java.simpleName)
                            .commit()
                    }
                    btnMore.setOnClickListener {
                        if(btnMore.text.toString() == "...more") {
                            text.maxLines = 100
                            btnMore.text = "...less"
                        }
                        else{
                            text.maxLines = 4
                            btnMore.text = "...more"
                        }
                    }
                }
            }
        })
    }

}