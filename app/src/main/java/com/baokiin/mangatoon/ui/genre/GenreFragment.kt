package com.baokiin.mangatoon.ui.genre

import android.os.Bundle
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.databinding.FragmentGenreBinding
import com.baokiin.mangatoon.ui.BaseFragment
import com.baokiin.mangatoon.ui.adapter.ItemGenreAdapter
import com.baokiin.mangatoon.ui.detailgener.DetailGenerFragment
import com.baokiin.mangatoon.ui.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreFragment : BaseFragment<FragmentGenreBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_genre
    }
    val viewModel:GenreViewModel by viewModel()
    override fun onCreateViews() {

        val adapter = ItemGenreAdapter{ genre, i ->
            val bundle = Bundle()
            bundle.putSerializable("data",genre)
            val fragment = DetailGenerFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.containerFragment,fragment)
                .addToBackStack(HomeFragment::class.java.simpleName)
                .commit()
        }
        baseBinding.viewmodel = viewModel
        baseBinding.adapterGener = adapter
        viewModel.getGenres()
        viewModel.genres.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.list_genre)
            }
        })
    }

}