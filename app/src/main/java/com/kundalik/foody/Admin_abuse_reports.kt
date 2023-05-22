package com.kundalik.foody

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.kundalik.foody.adapters.AbuseReportAdapter
import com.kundalik.foody.adapters.RecipeReportAdapter
import com.kundalik.foody.databinding.ActivityAdminAbuseReportsBinding
import com.kundalik.foody.databinding.ActivityAdminRecipesReportsBinding
import com.kundalik.foody.models.Abuse
import com.kundalik.foody.models.Recipe

class Admin_abuse_reports : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAbuseReportsBinding

    private lateinit var databaseReference: DatabaseReference
    private lateinit var reportRv: RecyclerView
    private lateinit var reportList: ArrayList<Abuse>
    private lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityAdminAbuseReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressbar = binding.listProgressbar
        reportRv = binding.abuseReportRecyclerView
        reportRv.layoutManager = LinearLayoutManager(this)
        reportRv.setHasFixedSize(true)

        binding.toolbar.productListingHeader.text = getString(R.string.abuse_report)

        //progressbar.visibility = View.VISIBLE

        //function call
        loadRecipeData()

    }

    private fun loadRecipeData() {

        reportList = ArrayList()

        databaseReference = FirebaseDatabase.getInstance().getReference("abuse")
        databaseReference.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                progressbar.visibility = View.VISIBLE
                if (snapshot.exists()) {

                    for (snaps in snapshot.getChildren()) {

                        val abuse = snaps.getValue(Abuse::class.java)
                        abuse.let {
                            if (abuse != null) {
                                reportList.add(abuse)

                                Log.d("firebasefoodrecipe", abuse.report)
                            }
                        }
                    }
                    reportRv.adapter = AbuseReportAdapter(reportList)
                    //dismiss progressbar
                    progressbar.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this@Admin_abuse_reports, "Unable to get recipe data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressbar.visibility = View.INVISIBLE
                Toast.makeText(this@Admin_abuse_reports, "Unable to get recipe data ${error.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }


}