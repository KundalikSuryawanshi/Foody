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
import com.kundalik.foody.databinding.ActivityAdminRecipesReportsBinding
import com.kundalik.foody.models.Recipe

class Admin_recipes_reports : AppCompatActivity() {

    private lateinit var binding: ActivityAdminRecipesReportsBinding

    private lateinit var databaseReference: DatabaseReference
    private lateinit var reportRv: RecyclerView
    private lateinit var reportList: ArrayList<Recipe>
    private lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityAdminRecipesReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressbar = binding.listProgressbar
        reportRv = binding.recipeReportRecyclerView
        reportRv.layoutManager = LinearLayoutManager(this)
        reportRv.setHasFixedSize(true)

        binding.toolbar.productListingHeader.text = getString(R.string.recipe_report)

        //progressbar.visibility = View.VISIBLE

        //function call
        loadRecipeData()

    }

    private fun loadRecipeData() {

        reportList = ArrayList()

        databaseReference = FirebaseDatabase.getInstance().getReference("recipe")
        databaseReference.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                progressbar.visibility = View.VISIBLE
                if (snapshot.exists()) {

                    for (snaps in snapshot.getChildren()) {

                        val recipe = snaps.getValue(Recipe::class.java)
                        recipe.let {
                            if (recipe != null) {
                                reportList.add(recipe)

                                Log.d("firebasefoodrecipe", recipe.name)
                            }
                        }
                    }
                    reportRv.adapter = RecipeReportAdapter(reportList)
                    //dismiss progressbar
                    progressbar.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this@Admin_recipes_reports, "Unable to get recipe data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressbar.visibility = View.INVISIBLE
                Toast.makeText(this@Admin_recipes_reports, "Unable to get recipe data ${error.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }


}