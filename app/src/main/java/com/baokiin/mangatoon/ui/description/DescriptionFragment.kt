package com.baokiin.mangatoon.ui.description


import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentDescriptionBinding
import com.baokiin.mangatoon.ui.base.BaseFragment
import com.baokiin.mangatoon.ui.adapter.ItemGenreDescriptionAdapter
import com.baokiin.mangatoon.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DescriptionFragment : BaseFragment<FragmentDescriptionBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_description
    }
    val viewmodel:DetailViewModel by sharedViewModel()
    override fun onCreateViews() {
        baseBinding.viewmodel =viewmodel
        val adapter = ItemGenreDescriptionAdapter{genre, i ->

        }
        baseBinding.adapter = adapter
        viewmodel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.genre_list)
            }
        })
    }

}