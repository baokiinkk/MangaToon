package com.baokiin.mangatoon.ui.library

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.databinding.FragmentLibraryBinding
import com.baokiin.mangatoon.ui.activity.LoginActivity
import com.baokiin.mangatoon.ui.detail.DetailFragment
import com.baokiin.mangatoon.ui.home.HomeFragment
import com.baokiin.mangatoon.utils.Utils
import com.baokiin.mangatoon.utils.Utils.ENDPOINT
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LibraryFragment : BaseFragment<FragmentLibraryBinding>() {
    val auth = Firebase.auth
    override fun getLayoutRes(): Int {
        return R.layout.fragment_library
    }

    val viewModel: LibraryViewModel by viewModel()
    override fun onCreateViews() {
        baseBinding.viewmodel = viewModel
        val adapter = ItemMangaAdapter { manga ->
            goDetailManga(manga)
        }
        baseBinding.adapter = adapter
        viewModel.getData()
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let { manga ->
                val tmp = manga.filter {
                    it.recents == true
                }
                adapter.submitList(tmp)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (auth.currentUser == null) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().viewpagerMainActivity.setCurrentItem(3, true)
        }
        viewModel.getData()
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