package com.kundalik.foody

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kundalik.foody.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        
        mBinding.tvEdit.setOnClickListener { 
            startActivity(Intent(this@ProfileActivity, UpdateProfileActivity::class.java))
        }

        mBinding.btnUpdateProfile.setOnClickListener {
            finish()
            Toast.makeText(this@ProfileActivity, "successfully logout", Toast.LENGTH_SHORT).show()
        }
    }
}