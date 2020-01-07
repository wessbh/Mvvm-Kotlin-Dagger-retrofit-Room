package com.wassimbh.projectdaggerretrofitmvvm.injection.component

import com.wassimbh.projectdaggerretrofitmvvm.ui.MainActivityViewModel
import com.wassimbh.projectdaggerretrofitmvvm.injection.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject (mainActivityViewModel: MainActivityViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}