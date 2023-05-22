package com.kundalik.foody.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kundalik.foody.R
import com.kundalik.foody.models.Recipe

class RecipeReportAdapter(val reportList: ArrayList<Recipe>): RecyclerView.Adapter<RecipeReportAdapter.ReportViewHolder>() {


    private lateinit var context: Context

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById(R.id.item_recipe_name) as TextView
        val category = itemView.findViewById(R.id.item_recipe_category) as TextView
        val time = itemView.findViewById(R.id.item_recipe_time) as TextView
        val image = itemView.findViewById(R.id.item_recipe_image) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_report, parent, false)
        return ReportViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val currentItem = reportList[position]

        holder.name.text = currentItem.name
        holder.category.text = currentItem.category
        holder.time.text = currentItem.timeToReady

        val imageUri = currentItem.image.toString()
        Glide.with(holder.itemView.context)
            .load(imageUri)
            .into(holder.image)

    }
}
