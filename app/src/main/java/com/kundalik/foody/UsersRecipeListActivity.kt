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
import com.kundalik.foody.adapters.RecipeAdapter
import com.kundalik.foody.databinding.ActivityUsersRecipeListBinding
import com.kundalik.foody.models.Recipe
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class UsersRecipeListActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityUsersRecipeListBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeArrayList: ArrayList<Recipe>

    private lateinit var progressbar: ProgressBar

    val calender = Calendar.getInstance().time
    val date = DateFormat.getDateInstance().format(calender)
    val year = Calendar.YEAR.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityUsersRecipeListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        
        progressbar = mBinding.listProgressbar
        recipeRecyclerView = mBinding.recipeRecyclerView
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeRecyclerView.setHasFixedSize(true)


        progressbar.visibility = View.VISIBLE


        //function call
        loadRecipeData()
    }

    private fun loadRecipeData() {

        recipeArrayList = ArrayList()

        databaseReference = FirebaseDatabase.getInstance().getReference("recipe")
        databaseReference.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                progressbar.visibility = View.VISIBLE
                if (snapshot.exists()) {

                    for (snaps in snapshot.getChildren()) {

                        val recipe = snaps.getValue(Recipe::class.java)
                        recipe.let {
                            if (recipe != null) {
                                    recipeArrayList.add(recipe)

                                Log.d("firebasefoodrecipe", recipe.name)
                            }
                        }
                    }
                    recipeRecyclerView.adapter = RecipeAdapter(recipeArrayList)
                    //dismiss progressbar
                    progressbar.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this@UsersRecipeListActivity, "Unable to get recipe data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressbar.visibility = View.INVISIBLE
                Toast.makeText(this@UsersRecipeListActivity, "Unable to get recipe data ${error.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }
}