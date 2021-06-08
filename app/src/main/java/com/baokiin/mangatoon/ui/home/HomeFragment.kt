package com.baokiin.mangatoon.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.adapter.ItemRecommendedAdapter
import com.baokiin.mangatoon.databinding.FragmentHomeBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemGenreHomeAdapter
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.ui.detail.DetailFragment
import com.baokiin.mangatoon.ui.detailgener.DetailGenerFragment
import com.baokiin.mangatoon.ui.search.SearchFragment
import com.baokiin.mangatoon.utils.Utils.DATA
import com.baokiin.mangatoon.utils.Utils.ENDPOINT

import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var recommendedAdapter: ItemRecommendedAdapter
    private lateinit var popularAdapter: ItemMangaAdapter
    private lateinit var genreAdapter: ItemGenreHomeAdapter
    private lateinit var manhuaAdapter: ItemMangaAdapter
    private lateinit var manhwaAdapter: ItemMangaAdapter
    private val viewModel: HomeViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

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

    private fun onClickItem() {
        recommendedAdapter = ItemRecommendedAdapter { manga ->
            goDetailManga(manga)
        }
        popularAdapter = ItemMangaAdapter { manga ->
            goDetailManga(manga)
        }
        genreAdapter = ItemGenreHomeAdapter { genre ->
            val bundle = Bundle()
            bundle.putSerializable(DATA, genre)
            val fragment = DetailGenerFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.containerFragment, fragment)
                .addToBackStack(HomeFragment::class.java.simpleName)
                .commit()
        }
        manhuaAdapter = ItemMangaAdapter { manga ->
            goDetailManga(manga)
        }
        manhwaAdapter = ItemMangaAdapter { manga ->
            goDetailManga(manga)
        }

        baseBinding.apply {
            layoutPopular.btnPopular.setOnClickListener {
                goDetailGener(Genre("Popular", "popular"))
            }
            layoutManhua.btnManhua.setOnClickListener {
                goDetailGener(Genre("Manhua", "manhua"))
            }
            layoutManhwa.btnManhwa.setOnClickListener {
                goDetailGener(Genre("Manhwa", "manhwa"))
            }
            btnSearch.setOnClickListener {
                transaction(SearchFragment(), Bundle())
            }
        }
    }

    private fun goDetailGener(gen: Genre) {
        val bundle = Bundle()
        bundle.putSerializable(DATA, gen)
        val fragment = DetailGenerFragment()
        fragment.arguments = bundle
        transaction(fragment, bundle)
    }

    private fun goDetailManga(manga: Manga) {
        val bundle = Bundle()
        bundle.putSerializable(ENDPOINT, manga)
        val fragment = DetailFragment()
        transaction(fragment, bundle)
    }

    fun transaction(fragment: Fragment, bundle: Bundle) {
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
}
