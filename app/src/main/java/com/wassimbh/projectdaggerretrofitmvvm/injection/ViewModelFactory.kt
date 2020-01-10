package com.wassimbh.projectdaggerretrofitmvvm.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wassimbh.projectdaggerretrofitmvvm.ui.activities.MainActivityViewModel
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.DetailsViewModel
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.FragmentsViewModel

class ViewModelFactory: ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel() as T
        }
        if (modelClass.isAssignableFrom(FragmentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FragmentsViewModel() as T
        }
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}