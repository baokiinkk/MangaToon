package com.baokiin.mangatoon.ui.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemGenreAdapter
import com.baokiin.mangatoon.adapter.ItemPopularAdapter
import com.baokiin.mangatoon.adapter.ItemRecommendedAdapter
import com.baokiin.mangatoon.databinding.FragmentHomeBinding
import com.baokiin.mangatoon.ui.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.layout_genre.view.*
import kotlinx.android.synthetic.main.layout_manhua_home.view.*
import kotlinx.android.synthetic.main.layout_popular_home.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }
    val viewModel:HomeViewModel by viewModel()
    override fun onCreateViews() {
        val recommendedAdapter = ItemRecommendedAdapter{ manga, i ->
        }
        val popularAdapter = ItemPopularAdapter{ manga, i ->
        }
        val genreAdapter = ItemGenreAdapter{ genre, i ->
        }
        val manhuaAdapter = ItemPopularAdapter{ manga, i ->

        }
        baseBinding.apply {
            viewmodel = viewModel
            viewPagerTitle.adapter = recommendedAdapter
            TabLayoutMediator(tabLayoutFragmentHome,viewPagerTitle,TabLayoutMediator.TabConfigurationStrategy{tab,pos->
            }).attach()

            layoutPopulatio.apply {
                recycleViewPopulation.adapter = popularAdapter
                recycleViewPopulation.layoutManager = GridLayoutManager(context,1,GridLayoutManager.HORIZONTAL,false)
            }
            layoutGener.apply {
                recycleViewGenres.adapter = genreAdapter
                recycleViewGenres.layoutManager = GridLayoutManager(context,1,GridLayoutManager.HORIZONTAL,false)
            }
            layoutManhua.apply {
                recycleViewManhua.adapter = manhuaAdapter
                recycleViewManhua.layoutManager = GridLayoutManager(context,1,GridLayoutManager.HORIZONTAL,false)

            }
           }
        viewModel.apply {
            getRecommended()
            getPopular(1)
            getManhua(1)
            getGenres()
            recommended.observe(viewLifecycleOwner, Observer {
                it?.let {
                    recommendedAdapter.submitList(it.manga_list.subList(0,5))
                }
            })
            popular.observe(viewLifecycleOwner, Observer {
                it?.let {
                    popularAdapter.submitList(it.manga_list)
                }
            })
            manHua.observe(viewLifecycleOwner, Observer {
                it?.let {
                    manhuaAdapter.submitList(it.manga_list)
                }
            })
            genres.observe(viewLifecycleOwner, Observer {
                it?.let {
                    genreAdapter.submitList(it.list_genre.subList(0,10))
                }
            })
        }
    }

}
