package com.baokiin.mangatoon.ui.mine

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.ui.activity.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_mine.view.*
import kotlinx.android.synthetic.main.layout_mine.view.*

class MineFragment : Fragment() {
    val auth = Firebase.auth
    lateinit var viewCurrent: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        viewCurrent = view
        view.apply {
            showView(view)
            txtDangXuat.setOnClickListener {
                auth.signOut()
                showView(this)
            }
            layoutMine.btnDangNhap.setOnClickListener {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        showView(viewCurrent)
    }

    fun showView(view: View) {
        auth.currentUser?.let {
            view.apply {
                imgavatarFragment.load(it.photoUrl)
                txtName.text = it.displayName
                layoutMine.visibility = View.GONE
                txtDangXuat.visibility = View.VISIBLE
            }
        }
        if (auth.currentUser == null) {
            view.layoutMine.visibility = View.VISIBLE
            view.txtDangXuat.visibility = View.GONE
        }
    }

}