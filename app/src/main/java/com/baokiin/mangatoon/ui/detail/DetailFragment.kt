package com.baokiin.mangatoon.ui.detail

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentDetailBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ViewPageAdapter
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.ui.activity.LoginActivity
import com.baokiin.mangatoon.ui.chap.ChapterFragment
import com.baokiin.mangatoon.ui.description.DescriptionFragment
import com.baokiin.mangatoon.utils.Utils.ENDPOINT
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail
    }

    private val viewModel: DetailViewModel by sharedViewModel()
    private lateinit var adapterViewPager: ViewPageAdapter
    override fun onCreateViews() {
        val manga: Manga? = arguments?.getSerializable(ENDPOINT) as Manga
        adapterViewPager = ViewPageAdapter(
            mutableListOf(DescriptionFragment(), ChapterFragment()),
            requireActivity()
        )
        manga?.let {
            it.endpoint?.let { it1 ->
                viewModel.getData(it1)
            }
            viewModel.isMangas(it.title)
        }

        baseBinding.apply {
            viewmodel = viewModel
            adapter = adapterViewPager
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        viewModel.apply {
            mangaLocal = manga
            isManga.observe(viewLifecycleOwner, Observer {
                it?.let {
                    viewModel.isLiked = it
                    baseBinding.btnHeart.progress = if (it) 0.5f else 0.0f
                }
            })
            isAuth.observe(viewLifecycleOwner, Observer {
                it?.let {
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    isAuth.postValue(null)
                }
            })
            data.observe(viewLifecycleOwner, Observer {
                it?.let {
                    baseBinding.shimmerDetail.visibility = View.GONE
                    baseBinding.shimmerDetail.stopShimmer()
                }
            })
        }

    }

    override fun onResume() {
        super.onResume()
        baseBinding.shimmerDetail.startShimmer()
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.apply {
            data.postValue(null)
            dataChapter.postValue(null)
            isAuth.postValue(null)
            isManga.postValue(null)
        }
    }

}