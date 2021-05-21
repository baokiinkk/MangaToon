package com.baokiin.mangatoon.ui.home

import android.view.View
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.ui.adapter.ItemGenreAdapter
import com.baokiin.mangatoon.ui.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.ui.adapter.ItemRecommendedAdapter
import com.baokiin.mangatoon.databinding.FragmentHomeBinding
import com.baokiin.mangatoon.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    private lateinit var recommendedAdapter: ItemRecommendedAdapter
    private lateinit var popularAdapter: ItemMangaAdapter
    private lateinit var genreAdapter: ItemGenreAdapter
    private lateinit var manhuaAdapter: ItemMangaAdapter
    private lateinit var manhwaAdapter: ItemMangaAdapter
    private val viewModel: HomeViewModel by viewModel()
    override fun onCreateViews() {
        onClickItem()

        baseBinding.apply {
            viewmodel = viewModel
            adapterRecommended = recommendedAdapter
            adapterPopular = popularAdapter
            adapteManhua = manhuaAdapter
            adapterManhwa = manhwaAdapter
            adapterGener = genreAdapter
        }
        viewModel.apply {
            getRecommended()
            getPopular(1)
            getManhua(1)
            getManhwa(1)
            getGenres()
            recommended.observe(viewLifecycleOwner, Observer {
                it?.let {
                    recommendedAdapter.submitList(it.manga_list.subList(0, 5))
                    baseBinding.contentShimmerRecommended.stopShimmer()
                    baseBinding.contentShimmerRecommended.visibility = View.GONE
                }
            })
            popular.observe(viewLifecycleOwner, Observer {
                it?.let {
                    popularAdapter.submitList(it.manga_list)
                    baseBinding.layoutPopular.contentShimmerPopular.stopShimmer()
                    baseBinding.layoutPopular.contentShimmerPopular.visibility = View.GONE
                }
            })
            manHua.observe(viewLifecycleOwner, Observer {
                it?.let {
                    manhuaAdapter.submitList(it.manga_list)
                    baseBinding.layoutManhua.contentShimmerManhua.stopShimmer()
                    baseBinding.layoutManhua.contentShimmerManhua.visibility = View.GONE
                }
            })
            manhwa.observe(viewLifecycleOwner, Observer {
                it?.let {
                    manhwaAdapter.submitList(it.manga_list)
                    baseBinding.layoutManhwa.contentShimmerManhwa.stopShimmer()
                    baseBinding.layoutManhwa.contentShimmerManhwa.visibility = View.GONE
                }
            })
            genres.observe(viewLifecycleOwner, Observer {
                it?.let {
                    genreAdapter.submitList(it.list_genre.subList(0, 10))
                    baseBinding.layoutGener.contentShimmerGener.stopShimmer()
                    baseBinding.layoutGener.contentShimmerGener.visibility = View.GONE
                }
            })
        }
    }

    private fun onClickItem() {
        recommendedAdapter = ItemRecommendedAdapter { manga, i ->
        }
        popularAdapter = ItemMangaAdapter { manga, i ->
        }
        genreAdapter = ItemGenreAdapter { genre, i ->
        }
        manhuaAdapter = ItemMangaAdapter { manga, i ->
        }
        manhwaAdapter = ItemMangaAdapter { manga, i ->
        }
    }
    override fun onResume() {
        super.onResume()
        baseBinding.apply {
            contentShimmerRecommended.startShimmer()
            layoutPopular.contentShimmerPopular.startShimmer()
            layoutManhua.contentShimmerManhua.startShimmer()
            layoutManhwa.contentShimmerManhwa.startShimmer()
            layoutGener.contentShimmerGener.startShimmer()
        }
    }
}
