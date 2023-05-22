package com.kundalik.foody

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kundalik.foody.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        progressBar = mBinding.progressBar

        mBinding.btnSignUp.setOnClickListener {
            checkInputs(
                mBinding.etUserEmail.text.toString(),
                mBinding.etRePassword.text.toString(),
                mBinding.etRePassword.text.toString()
            )
        }
        mBinding.tvSingIn.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        }

    }

    private fun checkInputs(email: String, password: String, rePassword: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()) {
            if (password.length >= 6) {
                if (password == rePassword) {
                    progressBar.visibility = View.VISIBLE
                    singUpUserByEmailandPass(email, password)
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Password should be same",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this@SignUpActivity, "Enter 8 Digit Password", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this@SignUpActivity, "Enter All the Fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun singUpUserByEmailandPass(email: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressBar.visibility = View.INVISIBLE
                val intent = Intent(this@SignUpActivity, PersonalDetailsActivity::class.java)
                intent.putExtra("email",email)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(
                    this@SignUpActivity,
                    "Unable to crate your account!${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}