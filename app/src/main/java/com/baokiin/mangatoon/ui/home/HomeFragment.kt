package com.baokiin.mangatoon.ui.home

import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemRecommendedAdapter
import com.baokiin.mangatoon.databinding.FragmentHomeBinding
import com.baokiin.mangatoon.ui.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }
    val viewModel:HomeViewModel by viewModel()
    override fun onCreateViews() {
        val adapter = ItemRecommendedAdapter{ manga, i ->
        }

        baseBinding.apply {
            viewmodel = viewModel
            viewPagerTitle.adapter = adapter
            TabLayoutMediator(tabLayoutFragmentHome,viewPagerTitle,TabLayoutMediator.TabConfigurationStrategy{tab,pos->

            }).attach()
        }
        viewModel.apply {
            getRecommended()
            recommended.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it.manga_list.subList(0,5))
                }
            })
        }
    }

}
