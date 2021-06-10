package com.baokiin.mangatoon.ui.search

import android.os.Bundle
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.databinding.FragmentSearchBinding
import com.baokiin.mangatoon.ui.detail.DetailFragment
import com.baokiin.mangatoon.ui.home.HomeFragment
import com.baokiin.mangatoon.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_search
    }
    val viewModel:SearchViewModel by viewModel()
    override fun onCreateViews() {
        val adapterManga = ItemMangaAdapter{manga ->
            goDetailManga(manga)
        }
        viewModel.search("a")
       baseBinding.apply {
           viewmodel = viewModel
           adapter = adapterManga

           btnBack.setOnClickListener {
               requireActivity().onBackPressed()
           }
           btnSearch.setOnQueryTextListener(object : OnQueryTextListener{
               override fun onQueryTextSubmit(query: String?): Boolean {
                   return false
               }

               override fun onQueryTextChange(newText: String?): Boolean {
                   newText?.let {
                       viewModel.search(it)
                   }
                   return false
               }

           })
       }

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterManga.submitList(it.manga_list)
            }
        })
    }
    private fun goDetailManga(manga: Manga) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.ENDPOINT, manga)
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