package com.dicoding.sub2.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.sub2.Followers
import com.dicoding.sub2.databinding.FragmentFollowersBinding
import com.dicoding.sub2.R

class FollowersAdapter(private val listReview: List<Followers>) :
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.followers, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = listReview[position].login
        viewHolder.username.text = listReview[position].htmlUrl
        viewHolder.organisasiflwrs.text = listReview[position].organizationsUrl
        Glide.with(viewHolder.itemView.context)
            .load(listReview[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.image)
    }

    override fun getItemCount() = listReview.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name_followers)
        val username: TextView = view.findViewById(R.id.username_followers)
        val organisasiflwrs: TextView = view.findViewById(R.id.org_followers)
        val image: ImageView = view.findViewById(R.id.user_avatar_followers)
    }


}
