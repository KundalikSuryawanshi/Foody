package com.kundalik.foody

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kundalik.foody.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //login shared pred
        sharedPref = getSharedPreferences("my_app_pref", Context.MODE_PRIVATE)
        val isLoggedIn =  sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
            finish()
        }

        progressBar = mBinding.progressBar

        mBinding.btnSignIn.setOnClickListener {
            checkInputs(
                mBinding.etUserEmail.text.toString(),
                mBinding.etUserPassword.text.toString()
            )
        }
        mBinding.tvSingUp.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }

        mBinding.btnAdmin.setOnClickListener {
            startActivity(Intent(this@SignInActivity, AdminLoginActivity::class.java))
            finish()
        }

    }

    private fun checkInputs(email: String, password: String) {
        if (mBinding.etUserEmail.text.toString().isNotEmpty() && mBinding.etUserPassword.text.toString().isNotEmpty()) {
            progressBar.visibility = View.VISIBLE
            signInUserByEmailandPassword(email, password)
        }
    }

    private fun signInUserByEmailandPassword(email: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressBar.visibility = View.INVISIBLE
                //changing state of login shared pref
                val editor = sharedPref.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()
                startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(
                    this@SignInActivity,
                    "Unable to Sign In your account!${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}