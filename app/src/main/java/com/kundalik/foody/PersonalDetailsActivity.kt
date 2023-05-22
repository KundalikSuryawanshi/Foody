package com.kundalik.foody

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.kundalik.foody.databinding.ActivityPersonalDetailsBinding
import com.kundalik.foody.models.User

class PersonalDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityPersonalDetailsBinding
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPersonalDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val intent = intent
        val email = intent.getStringExtra("email").toString()
        mBinding.etUserProfileEmail.setText(email)

        mBinding.btnSaveInfo.setOnClickListener {
            checkInputFields()
        }
    }

    private fun checkInputFields() {
        if (
            mBinding.etUserProfileEmail.text.toString().isNotEmpty() &&
            mBinding.etUserFirstName.text.toString().isNotEmpty() &&
            mBinding.etUserLastName.text.toString().isNotEmpty() &&
            mBinding.etUserMobile.text.toString().isNotEmpty() &&
            mBinding.etUserAddress.text.toString().isNotEmpty()
        ) {
            val email = mBinding.etUserProfileEmail.text.toString()
            val firstName = mBinding.etUserFirstName.text.toString()
            val lastName = mBinding.etUserLastName.text.toString()
            val mobile = mBinding.etUserMobile.text.toString()
            val address = mBinding.etUserAddress.text.toString()

            saveUserData(email, firstName, lastName, mobile, address)

        } else {
            Toast.makeText(
                this@PersonalDetailsActivity,
                "Complete all the fields",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveUserData(
        email: String,
        firstName: String,
        lastName: String,
        mobile: String,
        address: String,
    ) {
        database = FirebaseDatabase.getInstance()
        val category = mBinding.etUserFavCategoryFirst.toString()

        val user = User(email, firstName, lastName,mobile, address,category)

        val userRef = database.reference.child("users").push()
        userRef.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this@PersonalDetailsActivity, "Successfully saved!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@PersonalDetailsActivity, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this@PersonalDetailsActivity, "unable to save data &${it.message}", Toast.LENGTH_SHORT).show()
                finish()
            }

    }
}