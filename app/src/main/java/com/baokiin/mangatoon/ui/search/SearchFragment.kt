package com.baokiin.mangatoon.ui.search

import android.util.Log
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_search
    }
    val viewModel:SearchViewModel by viewModel()
    override fun onCreateViews() {
        val adapterManga = ItemMangaAdapter{manga ->

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

}