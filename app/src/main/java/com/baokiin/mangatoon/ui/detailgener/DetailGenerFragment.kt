package com.baokiin.mangatoon.ui.detailgener

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.databinding.FragmentDetailGenerBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemMangaPagingAdapter
import com.baokiin.mangatoon.ui.detail.DetailFragment
import com.baokiin.mangatoon.ui.home.HomeFragment
import com.baokiin.mangatoon.utils.Utils.DATA
import com.baokiin.mangatoon.utils.Utils.ENDPOINT
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailGenerFragment : BaseFragment<FragmentDetailGenerBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_gener
    }

    override fun onResume() {
        super.onResume()
        baseBinding.contentShimmerDetailGener.startShimmer()
    }

    val viewModel: DetailGenerViewModel by viewModel()
    override fun onCreateViews() {
        val itemMangaPagingAdapter = ItemMangaPagingAdapter { manga ->
            val bundle = Bundle()
            bundle.putSerializable(ENDPOINT, manga)
            val fragment = DetailFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    android.R.anim.fade_in,
                    android.R.anim.slide_out_right
                )
                .add(R.id.containerFragment, fragment)
                .addToBackStack(HomeFragment::class.java.simpleName)
                .commit()
        }
        val data: Genre = arguments?.get(DATA) as Genre
        data.endpoint?.let {
            viewModel.apply {
                if (it == "popular")
                    getDataPopular()
                else if (it == "manhua" || it == "manhwa")
                    getDataComic(it)
                else
                    getData(it)
                title = data.genre_name
            }
        }
        baseBinding.apply {
            viewmodel = viewModel
            adapter = itemMangaPagingAdapter
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                GlobalScope.launch {
                    itemMangaPagingAdapter.submitData(it)
                }
                baseBinding.contentShimmerDetailGener.stopShimmer()
                baseBinding.contentShimmerDetailGener.visibility = View.GONE
            }
        })
    }

}