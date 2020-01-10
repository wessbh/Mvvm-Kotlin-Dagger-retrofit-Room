package com.wassimbh.projectdaggerretrofitmvvm.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.utils.Converter


@Database(entities = arrayOf(Pokemon::class, Attacks::class), version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}