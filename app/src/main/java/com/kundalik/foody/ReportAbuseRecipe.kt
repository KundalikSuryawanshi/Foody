package com.kundalik.foody

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kundalik.foody.databinding.ActivityReportAbuseRecipeBinding
import com.kundalik.foody.models.Abuse

class ReportAbuseRecipe : AppCompatActivity() {

    private lateinit var mBinding: ActivityReportAbuseRecipeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityReportAbuseRecipeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val intent = intent
        progressBar = mBinding.progreebar

        mBinding.btnReport.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            val report = mBinding.abuseReport.text.toString()
            auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser?.email.toString()
            // you can take name and email as well
            val postUserId = intent.getStringExtra("postuserid").toString()
            val recipeid = intent.getStringExtra("recipeid").toString()

            if (report.isNotEmpty()) {
                val report = Abuse(
                    report,
                    currentUser,
                    postUserId,
                    recipeid
                )
                saveReportToFirebase(report)
            } else {
                Toast.makeText(this@ReportAbuseRecipe, "Enter report details!", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        mBinding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveReportToFirebase(report: Abuse) {

        database = FirebaseDatabase.getInstance()
        database.reference.child("abuse").push().setValue(report)
            .addOnSuccessListener {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@ReportAbuseRecipe, "report abuse success", Toast.LENGTH_SHORT)
                    .show()

                finish()
            }
            .addOnFailureListener {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@ReportAbuseRecipe, "failed ${it.message}", Toast.LENGTH_SHORT)
                    .show()

            }

    }
}