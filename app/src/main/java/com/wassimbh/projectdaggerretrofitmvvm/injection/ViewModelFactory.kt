package com.wassimbh.projectdaggerretrofitmvvm.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wassimbh.projectdaggerretrofitmvvm.ui.activities.MainActivityViewModel
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.PokemonListFragmentViewModel

class ViewModelFactory: ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel() as T
        }
        if (modelClass.isAssignableFrom(PokemonListFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonListFragmentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}