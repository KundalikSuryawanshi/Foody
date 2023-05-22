package com.kundalik.foody

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kundalik.foody.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAdminBinding.inflate(layoutInflater)

        mBinding.toolbar.productListingHeader.text = "Admin Panel"
        setContentView(mBinding.root)

        mBinding.userReportsBtn.setOnClickListener {
            startActivity(Intent(this@AdminActivity, Admin_user_reports::class.java))
        }
        mBinding.recipeReportBtn.setOnClickListener {
            startActivity(Intent(this@AdminActivity, Admin_recipes_reports::class.java))
        }
        mBinding.abuseReportBtn.setOnClickListener {
            startActivity(Intent(this@AdminActivity, Admin_abuse_reports::class.java))
        }
        mBinding.createAbureAdmin.setOnClickListener {
            startActivity(Intent(this@AdminActivity, ReportAbuseRecipe::class.java))
        }
        mBinding.createRecipeAdmin.setOnClickListener {
            startActivity(Intent(this@AdminActivity, CreateRecipeActivity::class.java))
        }
        mBinding.viewRecipeAdmin.setOnClickListener {
            startActivity(Intent(this@AdminActivity, UsersRecipeListActivity::class.java))
        }
    }
}