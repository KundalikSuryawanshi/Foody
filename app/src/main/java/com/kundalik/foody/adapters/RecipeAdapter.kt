package com.kundalik.foody.adapters

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager.SubsystemRestartTrackingCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.values
import com.kundalik.foody.R
import com.kundalik.foody.RecipeDetailsActivity
import com.kundalik.foody.models.Recipe


class RecipeAdapter(val recipeList: ArrayList<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private lateinit var context: Context
    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recipeName = itemView.findViewById(R.id.tv_recipe_title) as TextView
        val recipeCategory = itemView.findViewById(R.id.tv_recipe_category) as TextView
        val timeToReady = itemView.findViewById(R.id.tv_recipe_redy_time) as TextView
        val recipeImage = itemView.findViewById(R.id.iv_recipe_image) as ImageView

        init {
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val recipe = recipeList[position]
                    deleteRecipe(recipe)
                }
                true
            }
        }

    }

    private fun deleteRecipe(recipe: Recipe) {
        // Delete the recipe from Firebase Realtime Database

        val databaseReference = FirebaseDatabase.getInstance().reference.child("recipe")
        val key = FirebaseDatabase.getInstance().reference.child("recipe").key.toString()
        val keys = recipe.recipeId
        databaseReference.child(key).removeValue()

        // Delete the recipe from the local list and notify the adapter
        val position = recipeList.indexOf(recipe)
        if (position != -1) {
            recipeList.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {

        context = parent.context

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe_row_layout, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val currentRecipe = recipeList[position]

        holder.recipeName.text = currentRecipe.name
        holder.recipeCategory.text = currentRecipe.category
        holder.timeToReady.text = currentRecipe.timeToReady
        val imageUri = currentRecipe.image.toString()
        Glide.with(holder.itemView.context)
            .load(imageUri)
            .into(holder.recipeImage)

        val recipeimage = currentRecipe.image.toString()
        val recipename = currentRecipe.name.toString()
        val recieptitle = currentRecipe.title.toString()
        val timetoready = currentRecipe.timeToReady.toString()
        val category = currentRecipe.category.toString()
        val ingredients = currentRecipe.ingredients.toString()
        val process = currentRecipe.process.toString()
        val postdate = currentRecipe.postDate.toString()
        val postuserid = currentRecipe.postUserId.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, RecipeDetailsActivity::class.java)
            intent.putExtra("image", recipeimage)
            intent.putExtra("name", recipename)
            intent.putExtra("title", recieptitle)
            intent.putExtra("time",timetoready)
            intent.putExtra("category", category)
            intent.putExtra("ingredients", ingredients)
            intent.putExtra("process",process)
            intent.putExtra("postdate", postdate)
            intent.putExtra("postuserid", postuserid)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

}
