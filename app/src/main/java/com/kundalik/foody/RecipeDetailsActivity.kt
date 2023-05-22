package com.kundalik.foody

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.kundalik.foody.adapters.RecipeAdapter
import com.kundalik.foody.databinding.ActivityRecipeDetailsBinding
import com.kundalik.foody.databinding.ActivityUsersRecipeListBinding
import com.kundalik.foody.models.Recipe
import org.checkerframework.common.util.report.qual.ReportCreation

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRecipeDetailsBinding

    private lateinit var databaseReference: DatabaseReference
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeArrayList: ArrayList<Recipe>
    private lateinit var progressbar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val intent = intent

        mBinding.toolbar.productListingHeader.text = "Recipe Details"

        val name = intent.getStringExtra("name")
        val category = intent.getStringExtra("category")
        val ingredients = intent.getStringExtra("ingredients")
        val process = intent.getStringExtra("process")
        val image = intent.getStringExtra("image")

        val content = "Name - \n $name \n\n Category - \n $category \n\n Ingredients -\n $ingredients \n\n process - \n $process \n\n image - \n $image \n\n"

        val postuserid = intent.getStringExtra("postuserid")

        val imageuri = intent.getStringExtra("image")

        mBinding.tvDetailTitle.text = intent.getStringExtra("name")
        mBinding.tvDetailDesc.text = intent.getStringExtra("ingredients")
        mBinding.tvDetailContent.text = intent.getStringExtra("process")
        mBinding.tvDetailDate.text = intent.getStringExtra("postdate")
        mBinding.tvDetailCategory.text = intent.getStringExtra("category")

        Glide.with(mBinding.ivDetailRecipeImage1)
            .load(imageuri)
            .into(mBinding.ivDetailRecipeImage1)

        progressbar = mBinding.progressBar2
        recipeRecyclerView = mBinding.rvRecommendedBlogPost
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeRecyclerView.setHasFixedSize(true)

        mBinding.btnReportAbuse.setOnClickListener {
            val intent = Intent(this@RecipeDetailsActivity, ReportAbuseRecipe::class.java)

            intent.putExtra("postuserid", postuserid)

            startActivity(intent)
        }

        mBinding.btnShareRecipe.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, content)

            val chooserIntent = Intent.createChooser(sendIntent, "Share Recipe Vie...")
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(chooserIntent)
            }
        }

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
                    Toast.makeText(this@RecipeDetailsActivity, "Unable to get recipe data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressbar.visibility = View.INVISIBLE
                Toast.makeText(this@RecipeDetailsActivity, "Unable to get recipe data ${error.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }
}