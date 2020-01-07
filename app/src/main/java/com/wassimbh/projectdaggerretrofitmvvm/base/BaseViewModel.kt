package com.wassimbh.projectdaggerretrofitmvvm.base

import androidx.lifecycle.ViewModel
import com.wassimbh.projectdaggerretrofitmvvm.ui.MainActivityViewModel
import com.wassimbh.projectdaggerretrofitmvvm.injection.component.DaggerViewModelInjector
import com.wassimbh.projectdaggerretrofitmvvm.injection.component.ViewModelInjector
import com.wassimbh.projectdaggerretrofitmvvm.injection.module.NetworkModule

abstract class BaseViewModel:ViewModel(){

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainActivityViewModel -> injector.inject(this)
        }
    }


}