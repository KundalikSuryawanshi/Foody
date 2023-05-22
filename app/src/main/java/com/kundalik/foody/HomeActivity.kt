package com.kundalik.foody

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.os.IResultReceiver._Parcel
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kundalik.foody.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //navigate home screen button
        mBinding.btnCreateRecipe.setOnClickListener {
            startActivity(Intent(this@HomeActivity, CreateRecipeActivity::class.java))
        }

        mBinding.btnFovourite.setOnClickListener {
            startActivity(Intent(this@HomeActivity, UsersRecipeListActivity::class.java))
        }

        mBinding.btnAllRecipe.setOnClickListener {
            startActivity(Intent(this@HomeActivity, UsersRecipeListActivity::class.java))
        }

        mBinding.btnProfile.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
        }


//        val user = FirebaseAuth.getInstance().currentUser
//        if (user != null) {
//            startActivity(Intent(this@HomeActivity, SignInActivity::class.java))
//            finish()
//        }

    }

}