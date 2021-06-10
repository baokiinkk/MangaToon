package com.baokiin.mangatoon.ui.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.ui.activity.PhoneLoginActivity
import com.baokiin.mangatoon.utils.Utils.COIN
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_payment.view.*
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.view.*
import kotlinx.android.synthetic.main.fragment_payment.view.btnNap

class PaymentFragment : Fragment(), View.OnClickListener {
    private lateinit var tmpTxt: MutableList<TextView>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_payment, container, false)
        view.apply {
            tmpTxt = mutableListOf(txt22, txt45, txt109, txt199, txt299, txt499, txt999)
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            btnNap.setOnClickListener {
                val gia = view.txtGia.text.toString()
                showDiaLogBottom(gia)
            }
            tmpTxt.forEach {
                it.setOnClickListener(this@PaymentFragment)
            }
        }
        return view
    }

    override fun onClick(v: View) {
        txtGia.text = (v as TextView).text
    }

    @SuppressLint("InflateParams")
    fun showDiaLogBottom(gia: String) {
        val sheetDialog = context?.let { BottomSheetDialog(it, R.style.SheetDialog) }
        val viewDialog = layoutInflater.inflate(R.layout.bottom_sheet_payment, null)
        sheetDialog?.let { dialog ->
            viewDialog.apply {
                btnClose.setOnClickListener {
                    dialog.dismiss()
                }
                btnOrder.setOnClickListener {
                    val activity = PhoneLoginActivity()
                    val intent = Intent(requireContext(), activity::class.java)
                    intent.putExtra(COIN, gia.substring(0, gia.length - 2).toLong())
                    startActivity(intent)
                    dialog.dismiss()
                }
                txtTotal.text = gia
            }
            dialog.setContentView(viewDialog)
            dialog.show()
        }
    }
}