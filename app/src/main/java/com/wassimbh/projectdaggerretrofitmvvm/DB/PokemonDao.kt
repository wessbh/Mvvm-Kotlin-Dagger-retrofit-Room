package com.wassimbh.projectdaggerretrofitmvvm.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon

@Dao
interface PokemonDao {

    @Query ("Select * FROM Pokemon")
    fun getAll(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonList: List<Pokemon>)

    @Query("DELETE FROM Pokemon")
    fun deleteAllPokemones()
}