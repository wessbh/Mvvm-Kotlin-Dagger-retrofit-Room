package com.wassimbh.projectdaggerretrofitmvvm.injection.component

import com.wassimbh.projectdaggerretrofitmvvm.injection.module.AppModule
import com.wassimbh.projectdaggerretrofitmvvm.ui.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface ViewModelInjector {
    fun inject (mainActivityViewModel: MainActivityViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun appModule(networkModule: AppModule): Builder
    }
}