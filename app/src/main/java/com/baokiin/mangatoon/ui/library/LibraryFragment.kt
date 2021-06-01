package com.baokiin.mangatoon.ui.library

import android.util.Log
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.databinding.FragmentLibraryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LibraryFragment : BaseFragment<FragmentLibraryBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_library
    }
    val viewModel:LibraryViewModel by viewModel()
    override fun onCreateViews() {
        baseBinding.viewmodel = viewModel
        val adapter = ItemMangaAdapter{manga, i ->

        }
        baseBinding.adapter = adapter
        viewModel.getData()
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)

            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }


}