package com.baokiin.mangatoon.sns

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.UserSNS
import com.baokiin.mangatoon.ui.activity.PhoneLoginActivity
import com.baokiin.mangatoon.utils.SNSLoginType
import com.baokiin.mangatoon.utils.Utils.SNS_REQUEST_CODE_GOOGLE
import com.baokiin.mangatoon.utils.Utils.SNS_REQUEST_CODE_PHONE
import com.baokiin.mangatoon.utils.Utils.SNS_RESULT_CODE
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class SNSLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var fbCallbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        when (requestCode) {
            SNS_REQUEST_CODE_GOOGLE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                }
            }
            SNS_REQUEST_CODE_PHONE -> {
                if (resultCode == SNS_RESULT_CODE) {
                    val user = auth.currentUser

                    val userSNS = UserSNS(
                        snsLoginType = SNSLoginType.PhoneNumber,
                        user = user
                    )
                    handleCallbackLoginActivity(userSNS)
                }
            }
            else -> fbCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }


     // method functions


    /**
     * return snn user from SNS
     * with SNSLoginType
     */
    abstract fun onSNSUserResult(user: UserSNS)


    private fun init() {
        auth = Firebase.auth
        instanceGoogleSignIn()
    }

    private fun instanceGoogleSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun instanceFacebookSignIn() {
        fbCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(fbCallbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                handleCallbackLoginActivity(null)
            }

            override fun onError(error: FacebookException) {
            }
        })
    }

    protected fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SNS_REQUEST_CODE_GOOGLE)
    }

    protected fun signInFb() {
        instanceFacebookSignIn()
        LoginManager.getInstance()
            .logInWithReadPermissions(this, arrayListOf("public_profile"))
    }

    protected fun singInPhone() {
        val intent = Intent(this, PhoneLoginActivity::class.java)
        startActivityForResult(intent, SNS_REQUEST_CODE_PHONE)
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val userSNS = UserSNS(
                        snsLoginType = SNSLoginType.Google,
                        user = user
                    )
                    handleCallbackLoginActivity(userSNS)
                } else {
                    // If sign in fails, display a message to the user.
                    handleCallbackLoginActivity(null)
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val userSNS = UserSNS(
                        snsLoginType = SNSLoginType.Facebook,
                        user = user
                    )
                    handleCallbackLoginActivity(userSNS)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    handleCallbackLoginActivity(null)
                }
            }
    }

    private fun handleCallbackLoginActivity(user: UserSNS?) {
        user?.let {
            onSNSUserResult(it)
        }
    }

}
