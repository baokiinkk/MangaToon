package com.baokiin.mangatoon.ui.description


import android.os.Bundle
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentDescriptionBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemGenreDescriptionAdapter
import com.baokiin.mangatoon.ui.chapterdetail.BacktoChap
import com.baokiin.mangatoon.ui.chapterdetail.DetailChapterFragment
import com.baokiin.mangatoon.ui.detail.DetailFragment
import com.baokiin.mangatoon.ui.detail.DetailViewModel
import com.baokiin.mangatoon.utils.Utils
import com.baokiin.mangatoon.utils.Utils.ENDPOINT
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DescriptionFragment : BaseFragment<FragmentDescriptionBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_description
    }

    val viewModel: DetailViewModel by sharedViewModel()

    override fun onCreateViews() {
        val adapterDes = ItemGenreDescriptionAdapter()
        baseBinding.apply {
            viewmodel = viewModel
            adapter = adapterDes

        }
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let { manga->
                adapterDes.submitList(manga.genre_list)


                baseBinding.apply {
                    btnRead.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putString(
                            ENDPOINT,
                            manga.chapter[manga.chapter.size - 1].chapter_endpoint
                        )
                        bundle.putSerializable(Utils.DETAILMANGA,viewModel.data.value)
                        bundle.putSerializable(Utils.MANGA,viewModel.mangaLocal)
                        val fragment = DetailChapterFragment()
                        fragment.resiveOnBackFromDetailChap(object : BacktoChap {
                            override fun onClickBack() {
                                viewModel.getChapFromFirestore()
                            }

                        })
                        fragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.containerFragment, fragment)
                            .addToBackStack(DetailFragment::class.java.simpleName)
                            .commit()
                    }
                    btnMore.setOnClickListener {
                        if (btnMore.text.toString() == "...more") {
                            text.maxLines = 100
                            btnMore.text = "...less"
                        } else {
                            text.maxLines = 4
                            btnMore.text = "...more"
                        }
                    }
                }
            }
        })
    }

}