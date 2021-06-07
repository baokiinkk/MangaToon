package com.baokiin.mangatoon.ui.genre

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.databinding.FragmentGenreBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemGenreAdapter
import com.baokiin.mangatoon.ui.detailgener.DetailGenerFragment
import com.baokiin.mangatoon.ui.home.HomeFragment
import com.baokiin.mangatoon.utils.Utils.DATA
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreFragment : BaseFragment<FragmentGenreBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_genre
    }
    val viewModel:GenreViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        baseBinding.contentShimmerGener.startShimmer()
    }

    override fun onCreateViews() {
        val adapter = ItemGenreAdapter{ genre ->
            val bundle = Bundle()
            bundle.putSerializable(DATA,genre)
            val fragment = DetailGenerFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    android.R.anim.fade_in,
                    android.R.anim.slide_out_right
                )
                .add(R.id.containerFragment,fragment)
                .addToBackStack(HomeFragment::class.java.simpleName)
                .commit()
        }

        baseBinding.apply {
            viewmodel = viewModel
            adapterGener = adapter
        }

        viewModel.apply {
            getGenres()
            genres.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it.list_genre)
                    baseBinding.contentShimmerGener.stopShimmer()
                    baseBinding.contentShimmerGener.visibility = View.GONE
                }
            })
        }
    }

}