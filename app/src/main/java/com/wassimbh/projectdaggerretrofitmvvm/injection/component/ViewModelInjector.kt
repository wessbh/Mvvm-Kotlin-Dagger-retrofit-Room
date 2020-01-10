package com.wassimbh.projectdaggerretrofitmvvm.injection.component

import com.wassimbh.projectdaggerretrofitmvvm.injection.module.AppModule
import com.wassimbh.projectdaggerretrofitmvvm.ui.activities.MainActivityViewModel
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.DetailsViewModel
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.FragmentsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface ViewModelInjector {
    fun inject (mainActivityViewModel: MainActivityViewModel)
    fun inject(pokemonListFragmentViewModel: FragmentsViewModel)
    fun inject(detailsViewModel: DetailsViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun appModule(networkModule: AppModule): Builder
    }
}