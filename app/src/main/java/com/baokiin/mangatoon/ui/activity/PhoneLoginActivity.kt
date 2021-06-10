package com.baokiin.mangatoon.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.utils.Utils.COIN
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_phone_login.*
import kotlinx.android.synthetic.main.phone_digit_code.view.*
import java.util.concurrent.TimeUnit


class PhoneLoginActivity : AppCompatActivity() {
    private lateinit var phoneCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var forceResend: PhoneAuthProvider.ForceResendingToken
    private lateinit var mVerficationId: String
    private lateinit var auth: FirebaseAuth
    private var gia = 0L
    var checkCode = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)
        gia = intent.getLongExtra(COIN,0)
        init()
    }
    private fun init() {
        instancePhoneSignIn()
        auth = Firebase.auth
        buttonGetCode.setOnClickListener {
            if(!checkCode) {
                val phone = edit_phoneNumber.text.toString()
                if (phone.isEmpty() || phone.length != 9) {
                    Toast.makeText(
                        this@PhoneLoginActivity,
                        "Please enter phone number without 0",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    startPhoneNumberVerification("+84$phone")
                }
            }
            else{
                val code = phone_layout.edit_code.text.toString()
                verifyPhoneNumberWithCode(mVerficationId, code)
            }
        }
        phone_layout.apply {
            buttonResend.setOnClickListener {
                val phone = edit_phoneNumber.text.toString()
                resendVerificationCode(phone, forceResend)
            }
        }

    }

    private fun startPhoneNumberVerification(phoneNumber: String) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(phoneCallBack)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(phoneCallBack)          // OnVerificationStateChangedCallbacks
            .setForceResendingToken(token)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        verificationId?.let {
            val credential = PhoneAuthProvider.getCredential(it, code)
            signInWithPhoneAuthCredential(credential)

        }


    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.currentUser?.let { user->
            user.linkWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val db = Firebase.firestore
                        db.collection(user.uid).document("Coin").get()
                            .addOnSuccessListener {
                                val giaOld = it["coin"] as Long
                                db.collection(user.uid).document("Coin")
                                    .update("coin" , gia+giaOld)

                            }
                        user.updatePhoneNumber(credential)
                        user.unlink(credential.provider)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Authentication failed by code error.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun instancePhoneSignIn() {
        phoneCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Toast.makeText(this@PhoneLoginActivity, p0.smsCode, Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@PhoneLoginActivity, p0.message, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                mVerficationId = p0
                forceResend = p1
                Toast.makeText(
                    this@PhoneLoginActivity,
                    "Verification code sent",
                    Toast.LENGTH_SHORT
                ).show()
                //hide phone show code
                checkCode = true
                phone_layout.visibility = View.VISIBLE
                layout_phone.visibility = View.GONE

            }
        }
    }
}
