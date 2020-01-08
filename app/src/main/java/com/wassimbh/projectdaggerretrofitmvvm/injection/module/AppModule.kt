package com.wassimbh.projectdaggerretrofitmvvm.injection.module

import com.wassimbh.projectdaggerretrofitmvvm.DB.AppDatabase
import com.wassimbh.projectdaggerretrofitmvvm.DB.PokemonDao
import com.wassimbh.projectdaggerretrofitmvvm.MyApplicationClass
import com.wassimbh.projectdaggerretrofitmvvm.api.ApiServices
import com.wassimbh.projectdaggerretrofitmvvm.repository.Repository
import com.wassimbh.projectdaggerretrofitmvvm.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module

@Suppress("unused")
object AppModule{

    @Provides
    @Singleton
    internal fun providePokemonApi(retrofit: Retrofit.Builder): ApiServices {
        return retrofit.build().create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.base_url)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    internal fun provideDao(db: AppDatabase): PokemonDao {
        return db.pokemonDao()
    }
    @Provides
    @Singleton
     internal fun provideRoomDatabase(): AppDatabase{
        return MyApplicationClass.database!!
    }

    @Provides
    @Singleton
    internal fun provideRepository(apiServices: ApiServices, pokemonDao: PokemonDao): Repository{
        return Repository(apiServices, pokemonDao)
    }
}