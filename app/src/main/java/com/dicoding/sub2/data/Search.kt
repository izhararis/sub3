package com.dicoding.sub2.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.dicoding.sub2.R
import com.dicoding.sub2.search.SearchResponse

class Search(
    private val item: List<SearchResponse.ItemsItem?>?,
    private val callback: ((data: SearchResponse.ItemsItem?) -> Unit)
) : Adapter<Search.SearchViewHolder>() {
    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.user_avatar_user)
        val name: TextView = itemView.findViewById(R.id.name_user)
        val username: TextView = itemView.findViewById(R.id.user_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.name.text = item?.get(position)?.login
        holder.username.text = item?.get(position)?.url
        Glide.with(holder.itemView.context)
            .load(item?.get(position)?.avatarUrl)
            .circleCrop()
            .into(holder.image)

        holder.itemView.setOnClickListener {
            callback.invoke(item?.get(position))
        }
    }

    override fun getItemCount(): Int = item?.size ?: 0
}