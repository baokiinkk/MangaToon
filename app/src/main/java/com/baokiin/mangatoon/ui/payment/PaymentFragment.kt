package com.baokiin.mangatoon.ui.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.generated.callback.OnClickListener
import com.baokiin.mangatoon.ui.activity.PhoneLoginActivity
import com.baokiin.mangatoon.utils.Utils.COIN
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.view.*

class PaymentFragment : Fragment(), View.OnClickListener {
    lateinit var tmpTxt:MutableList<TextView>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_payment, container, false)
        view.apply {
            tmpTxt = mutableListOf(txt22,txt45,txt109,txt199,txt299,txt499,txt999)
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            btnNap.setOnClickListener {
                val gia = view.txtGia.text.toString()
                val intent = Intent(requireContext(),PhoneLoginActivity::class.java)
                intent.putExtra(COIN,gia.substring(0,gia.length-2).toLong())
                startActivity(intent)
                requireActivity().onBackPressed()
            }
            tmpTxt.forEach {
                it.setOnClickListener(this@PaymentFragment)
            }
        }
        return view
    }

    override fun onClick(v:View){
        txtGia.text = (v as TextView).text
    }

}