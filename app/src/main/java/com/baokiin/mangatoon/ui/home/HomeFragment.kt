package com.baokiin.mangatoon.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.ui.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.ui.adapter.ItemRecommendedAdapter
import com.baokiin.mangatoon.databinding.FragmentHomeBinding
import com.baokiin.mangatoon.ui.base.BaseFragment
import com.baokiin.mangatoon.ui.adapter.ItemGenreHomeAdapter
import com.baokiin.mangatoon.ui.detail.DetailFragment

import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    private lateinit var recommendedAdapter: ItemRecommendedAdapter
    private lateinit var popularAdapter: ItemMangaAdapter
    private lateinit var genreAdapter: ItemGenreHomeAdapter
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
            goDetailManga(manga)
        }
        popularAdapter = ItemMangaAdapter { manga, i ->
            goDetailManga(manga)
        }
        genreAdapter = ItemGenreHomeAdapter { genre, i ->

        }
        manhuaAdapter = ItemMangaAdapter { manga, i ->
            goDetailManga(manga)
        }
        manhwaAdapter = ItemMangaAdapter { manga, i ->
            goDetailManga(manga)
        }
    }

    fun goDetailManga(manga: Manga){
        val bundle = Bundle()
        bundle.putString("endPoint",manga.endpoint)
        val fragment = DetailFragment()
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.containerFragment,fragment)
            .addToBackStack(HomeFragment::class.java.simpleName)
            .commit()
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
