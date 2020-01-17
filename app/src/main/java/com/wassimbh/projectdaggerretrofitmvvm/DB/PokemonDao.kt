package com.wassimbh.projectdaggerretrofitmvvm.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon

@Dao
interface PokemonDao {
// ------------------------------   SELECT QUERIES  ------------------------------------------ \\

    @Query ("Select * FROM Pokemon")
    fun getAll(): List<Pokemon>
    @Query ("Select * FROM Pokemon Where types = :type")
    fun getAllByType(type: String): List<Pokemon>
    @Query ("Select * From Attacks Where pokemone_id = :pokemon_id")
    fun getAttacks(pokemon_id: String): List<Attacks>

    // ------------------------------   INSERT QUERIES  ------------------------------------------ \\
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemons(pokemonList: List<Pokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttacks(attacks: List<Attacks>)

    // ------------------------------   DELETE QUERIES  ------------------------------------------ \\

    @Query("DELETE FROM Pokemon")
    fun deleteAllPokemones()
    @Query("DELETE FROM Attacks")
    fun deleteAttacks()
    @Query("DELETE FROM Attacks WHERE pokemone_id = :pokemon_id")
    fun deleteAttacksByPokemon(pokemon_id: String)

}