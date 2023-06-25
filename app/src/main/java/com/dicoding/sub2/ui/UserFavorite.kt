package com.dicoding.sub2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sub2.DetailUser
import com.dicoding.sub2.R
import com.dicoding.sub2.data.Search
import com.dicoding.sub2.local.DbModule

class UserFavorite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val recyclerView: RecyclerView = findViewById(R.id.rc_view_favorite)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val liveData = DbModule.getInstance().userDao().loadall()
        liveData.observe(this) { itemList ->
            recyclerView.adapter = Search(itemList) {
                val intent = Intent(this@UserFavorite, DetailUser::class.java)
                intent.putExtra("item", it)
                startActivity(intent)
            }
        }
    }
}