package com.baokiin.mangatoon.ui.detailgener

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.databinding.FragmentDetailGenerBinding
import com.baokiin.mangatoon.ui.BaseFragment
import com.baokiin.mangatoon.ui.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.ui.adapter.ItemMangaPagingAdapter
import com.baokiin.mangatoon.ui.detail.DetailFragment
import com.baokiin.mangatoon.ui.home.HomeFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailGenerFragment : BaseFragment<FragmentDetailGenerBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_gener
    }

    val viewModel: DetailGenerViewModel by viewModel()
    override fun onCreateViews() {
        baseBinding.viewmodel = viewModel
        val data: Genre = arguments?.get("data") as Genre
        data.endpoint?.let {
            viewModel.apply {
                getData(it)
                title = data.genre_name
            }
        }
        baseBinding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val adapter = ItemMangaPagingAdapter { manga, i ->
            val bundle = Bundle()
            bundle.putString("endPoint",manga.endpoint)
            val fragment = DetailFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.containerFragment,fragment)
                .addToBackStack(HomeFragment::class.java.simpleName)
                .commit()
        }
        baseBinding.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                GlobalScope.launch {
                    adapter.submitData(it)
                }
            }
        })
    }

}