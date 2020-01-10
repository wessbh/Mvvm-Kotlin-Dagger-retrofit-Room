package com.wassimbh.projectdaggerretrofitmvvm.models

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonAndAttacks (
    @Embedded
    var pokemon: Pokemon,
    @Relation(parentColumn = "id", entityColumn = "name", entity = Attacks::class)
    var attackList: List<Attacks>
)