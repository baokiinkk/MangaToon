package com.baokiin.mangatoon.ui.mine

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.databinding.FragmentMineBinding
import com.baokiin.mangatoon.ui.activity.LoginActivity
import com.baokiin.mangatoon.ui.home.HomeFragment
import com.baokiin.mangatoon.ui.payment.PaymentFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.layout_mine.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MineFragment : BaseFragment<FragmentMineBinding>() {
    val viewModel: MineViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine
    }

    override fun onCreateViews() {
        instanceGoogleSignIn()
        baseBinding.apply {
            viewmodel = viewModel
            txtDangXuat.setOnClickListener {
                viewModel.upData()
                viewModel.auth.value?.signOut()
                googleSignInClient.signOut()
                showView()
            }
            layoutMine.btnDangNhap.setOnClickListener {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
            btnNap.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.containerFragment, PaymentFragment())
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        android.R.anim.fade_in,
                        android.R.anim.slide_out_right
                    )
                    .addToBackStack(HomeFragment::class.java.simpleName)
                    .commit()
            }
        }
        viewModel.auth.observe(viewLifecycleOwner, Observer {
            showView()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.auth.postValue(Firebase.auth)
    }

    private fun showView() {
        viewModel.auth.value?.currentUser?.let {
            baseBinding.apply {
//                val profileUpdates = UserProfileChangeRequest.Builder()
//                    .setDisplayName("baokiin").build()
//                it.updateProfile(profileUpdates)
                viewModel.getCoinUser(it)
                layoutMine.visibility = View.GONE
                txtDangXuat.visibility = View.VISIBLE
            }
        }
        if (viewModel.auth.value?.currentUser == null) {
            baseBinding.layoutMine.visibility = View.VISIBLE
            baseBinding.txtDangXuat.visibility = View.GONE
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