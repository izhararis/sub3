package com.dicoding.sub2.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.sub2.Following
import com.dicoding.sub2.databinding.FragmentFollowingBinding
import com.dicoding.sub2.R

class FollowingAdapter(private val listReview: List<Following>) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.following, viewGroup, false)
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
        val name: TextView = view.findViewById(R.id.name_following)
        val username: TextView = view.findViewById(R.id.username_following)
        val organisasiflwrs: TextView = view.findViewById(R.id.org_following)
        val image: ImageView = view.findViewById(R.id.user_avatar_following)
    }


}
