package com.wassimbh.projectdaggerretrofitmvvm.base

import androidx.lifecycle.ViewModel
import com.wassimbh.projectdaggerretrofitmvvm.injection.component.DaggerViewModelInjector
import com.wassimbh.projectdaggerretrofitmvvm.injection.component.ViewModelInjector
import com.wassimbh.projectdaggerretrofitmvvm.injection.module.AppModule
import com.wassimbh.projectdaggerretrofitmvvm.ui.activities.MainActivityViewModel
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.DetailsViewModel
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.FragmentsViewModel

abstract class BaseViewModel:ViewModel(){

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .appModule(AppModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainActivityViewModel -> injector.inject(this)
        }
        when(this){
            is FragmentsViewModel -> injector.inject(this)
        }
        when(this){
            is DetailsViewModel -> injector.inject(this)
        }
    }


}