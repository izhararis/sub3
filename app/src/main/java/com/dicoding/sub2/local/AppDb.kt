package com.dicoding.sub2.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.sub2.search.SearchResponse

@Database(entities = [SearchResponse.ItemsItem::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase(){
    abstract fun userDao(): UserDao
}