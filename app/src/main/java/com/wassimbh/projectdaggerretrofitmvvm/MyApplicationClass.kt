package com.wassimbh.projectdaggerretrofitmvvm

import android.app.Application
import androidx.room.Room
import com.wassimbh.projectdaggerretrofitmvvm.DB.AppDatabase

class MyApplicationClass: Application(){

    companion object{
        var database: AppDatabase? = null
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "Pokemon").build()
    }
}