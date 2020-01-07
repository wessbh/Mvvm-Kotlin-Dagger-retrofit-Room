package com.wassimbh.projectdaggerretrofitmvvm.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.wassimbh.projectdaggerretrofitmvvm.DB.AppDatabase
import com.wassimbh.projectdaggerretrofitmvvm.ui.MainActivityViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts").build()
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(db.pokemonDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}