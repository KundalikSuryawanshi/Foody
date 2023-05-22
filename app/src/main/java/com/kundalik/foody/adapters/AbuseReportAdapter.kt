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
import com.kundalik.foody.models.Abuse
import com.kundalik.foody.models.Recipe
import com.kundalik.foody.models.User

class AbuseReportAdapter (val reportList : ArrayList<Abuse>): RecyclerView.Adapter<AbuseReportAdapter.ReportViewHolder>() {

    private lateinit var context: Context

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val reportid = itemView.findViewById(R.id.item_report_id) as TextView
        val report = itemView.findViewById(R.id.item_report_name) as TextView
        val reporter = itemView.findViewById(R.id.item_reporter) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_abuse_report, parent, false)
        return ReportViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val currentItem = reportList[position]

        holder.reportid.text = currentItem.report
        holder. report.text = currentItem.report
        holder.reporter.text = currentItem.informer


    }
}
