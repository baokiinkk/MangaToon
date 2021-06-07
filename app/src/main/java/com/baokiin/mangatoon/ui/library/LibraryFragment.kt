package com.baokiin.mangatoon.ui.library

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.databinding.FragmentLibraryBinding
import com.baokiin.mangatoon.ui.activity.LoginActivity
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

        }
        baseBinding.adapter = adapter
        viewModel.getData()
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let { manga ->
                adapter.submitList(manga)
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


}