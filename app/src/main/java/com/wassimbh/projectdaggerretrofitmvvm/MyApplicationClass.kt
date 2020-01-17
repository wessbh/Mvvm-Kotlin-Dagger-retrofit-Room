package com.wassimbh.projectdaggerretrofitmvvm

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.play.core.splitcompat.SplitCompat
import com.wassimbh.projectdaggerretrofitmvvm.DB.AppDatabase

class MyApplicationClass: Application(){

    companion object{
        var database: AppDatabase? = null
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "Pokemon").build()
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}