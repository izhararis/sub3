package com.dicoding.sub2.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sub2.ApiConfig
import com.dicoding.sub2.R
import com.dicoding.sub2.data.FollowersAdapter
import com.dicoding.sub2.Followers
import com.dicoding.sub2.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment(private val username: String) : Fragment() {
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getApiFollowers()
    }

    private fun getApiFollowers() {
        binding.progressBarFollowers.visibility = View.VISIBLE
        val follower = ApiConfig.getApiFollowers().getFollowers(username)
        follower.enqueue(object : Callback<List<Followers>> {
            override fun onResponse(
                call: Call<List<Followers>>,
                response: Response<List<Followers>>
            ) {
                binding.progressBarFollowers.visibility = View.INVISIBLE
                val data = response.body()
                if (data != null) {
                    val recyclerView: RecyclerView = requireView().findViewById(R.id.rc_view_followers)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = FollowersAdapter(data)
                }
            }

            override fun onFailure(call: Call<List<Followers>>, t: Throwable) {
                binding.progressBarFollowers.visibility = View.INVISIBLE
                Log.d("Followers", "Error")
            }

        })
    }

}