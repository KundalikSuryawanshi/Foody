package com.kundalik.foody

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.kundalik.foody.databinding.ActivityAdminLoginBinding

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminLoginBinding
    private lateinit var useremail: TextInputEditText
    private lateinit var userpass: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = "cmcs@gmail.com"
        val password = "cmcs"

        useremail = findViewById(R.id.et_user_email)
        userpass = findViewById(R.id.et_user_password)

        binding.btnSignIn.setOnClickListener {
            if (userpass.text.toString().isNotEmpty() && useremail.text.toString().isNotEmpty()) {
                startActivity(Intent(this@AdminLoginActivity, AdminActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@AdminLoginActivity, "You Are not an Admin", Toast.LENGTH_LONG)
                    .show()
            }


        }

        binding.tvUseApp.setOnClickListener {
            startActivity(Intent(this@AdminLoginActivity, HomeActivity::class.java))
            finish()
        }


    }
}