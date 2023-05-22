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
import com.kundalik.foody.adapters.RecipeReportAdapter
import com.kundalik.foody.adapters.UsersReportAdapter
import com.kundalik.foody.databinding.ActivityAdminUserReportsBinding
import com.kundalik.foody.models.User

class Admin_user_reports : AppCompatActivity() {

    private lateinit var binding: ActivityAdminUserReportsBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var reportRv: RecyclerView
    private lateinit var reportList: ArrayList<User>
    private lateinit var progressbar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUserReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressbar = binding.listProgressbar
        reportRv = binding.userReportRecyclerView
        reportRv.layoutManager = LinearLayoutManager(this)
        reportRv.setHasFixedSize(true)

        binding.toolbar.productListingHeader.text = getString(R.string.user_report)

        //progressbar.visibility = View.VISIBLE

        //function call
        loadRecipeData()

    }

    private fun loadRecipeData() {

        reportList = ArrayList()

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                progressbar.visibility = View.VISIBLE
                if (snapshot.exists()) {

                    for (snaps in snapshot.getChildren()) {

                        val user = snaps.getValue(User::class.java)
                        user.let {
                            if (user != null) {
                                reportList.add(user)

                                Log.d("firebasefoodrecipe", user.favCategory)
                            }
                        }
                    }
                    reportRv.adapter = UsersReportAdapter(reportList)
                    //dismiss progressbar
                    progressbar.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this@Admin_user_reports, "Unable to get recipe data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressbar.visibility = View.INVISIBLE
                Toast.makeText(this@Admin_user_reports, "Unable to get recipe data ${error.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }


}