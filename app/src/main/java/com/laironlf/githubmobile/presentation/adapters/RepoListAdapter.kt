package com.laironlf.githubmobile.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.laironlf.githubmobile.R
import com.laironlf.githubmobile.domain.entities.Repo

class RepoListAdapter(private val onItemClick: (String, String) -> Unit) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    var items: List<Repo> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.listitem_repo_title)
        val tvDescription: TextView = view.findViewById(R.id.listitem_repo_description)
        val tvLang: TextView = view.findViewById(R.id.listitem_repo_lang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_repo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items[position].description == null) holder.tvDescription.visibility = View.GONE
        else holder.tvDescription.text = items[position].description
        holder.tvTitle.text = items[position].repoName
        holder.tvLang.text = items[position].language
        holder.tvLang.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                items[position].getColor()
            )
        )
        holder.itemView.setOnClickListener {
            onItemClick(
                items[position].id.toString(), items[position].repoName
            )
        }
    }

    override fun getItemCount() = items.size
}