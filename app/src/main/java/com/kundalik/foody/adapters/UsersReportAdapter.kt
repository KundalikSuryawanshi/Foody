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
import com.kundalik.foody.models.User

class UsersReportAdapter (val reportList : ArrayList<User>): RecyclerView.Adapter<UsersReportAdapter.ReportViewHolder>() {

    private lateinit var context: Context

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById(R.id.item_username) as TextView
        val email = itemView.findViewById(R.id.item_userEmail) as TextView
        val mobile = itemView.findViewById(R.id.item_userMobile) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user_report, parent, false)
        return ReportViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val currentItem = reportList[position]

        holder.name.text = currentItem.firstName
        holder.email.text = currentItem.userEmail
        holder.mobile.text = currentItem.userMobile


    }
}
