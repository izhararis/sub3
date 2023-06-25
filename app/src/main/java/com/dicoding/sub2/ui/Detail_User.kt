package com.dicoding.sub2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.sub2.data.PageAdapter
import com.dicoding.sub2.search.DetailResponse
import com.dicoding.sub2.search.SearchResponse
import com.dicoding.sub2.databinding.ActivityDetailUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUser : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var item: SearchResponse.ItemsItem? = null
    private var isExistFavorite: Boolean = false
    private lateinit var binding: ActivityDetailUserBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        item = intent.getParcelableExtra("item")
        val username = item?.login ?: "izhar"
        val sectionsPagerAdapter = PageAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailUser(username)
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show()

        val favoriteBtn: FloatingActionButton = findViewById(R.id.btn_favorite)
        isExistFavorite = mainViewModel.isDataExist(item?.id ?: 0)
        if (isExistFavorite) {
            favoriteBtn.setImageResource(R.drawable.ic_favorite)
        } else {
            favoriteBtn.setImageResource(R.drawable.ic_favorite_border_black)
        }

        favoriteBtn.setOnClickListener {
            if (isExistFavorite) {
                mainViewModel.deleteData(item!!)
                isExistFavorite = false
                favoriteBtn.setImageResource(R.drawable.ic_favorite_border_black)
            } else {
                mainViewModel.saveData(item!!)
                isExistFavorite = true
                favoriteBtn.setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    private fun detailUser(name: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().detailUser(name)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                val data = response.body()
                binding.apply {
                    userUsername.text = data?.login
                    userName.text = data?.name
                    userCompany.text = data?.company
                    userLocation.text = data?.location
                    userFollowers.text = data?.followers.toString()
                    userFollowing.text = data?.following.toString()
                    userRepository.text = data?.publicRepos.toString()
                    Glide.with(binding.userAvatar)
                        .load(data?.avatarUrl)
                        .circleCrop()
                        .into(binding.userAvatar)
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                Log.d("DetailUserActivity", "Error: ${t.message}")
            }
        })
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

}