package com.baokiin.mangatoon.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.ui.activity.LoginActivity
import com.baokiin.mangatoon.ui.home.HomeFragment
import com.baokiin.mangatoon.ui.payment.PaymentFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_mine.view.*
import kotlinx.android.synthetic.main.layout_mine.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MineFragment : Fragment() {
    val auth = Firebase.auth
    lateinit var viewCurrent: View
    val viewModel:MineViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        instanceGoogleSignIn()
        viewCurrent = view
        view.apply {
            showView(view)
            txtDangXuat.setOnClickListener {
                viewModel.upData()
                auth.signOut()
                googleSignInClient.signOut()
                showView(this)
            }
            layoutMine.btnDangNhap.setOnClickListener {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
            btnNap.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.containerFragment,PaymentFragment())
                    .addToBackStack(HomeFragment::class.java.simpleName)
                    .commit()
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

                txtMail.text = it.email?:it.phoneNumber?:""
//                val profileUpdates = UserProfileChangeRequest.Builder()
//                    .setDisplayName("baokiin").build()
//                it.updateProfile(profileUpdates)
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
    private fun instanceGoogleSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

}