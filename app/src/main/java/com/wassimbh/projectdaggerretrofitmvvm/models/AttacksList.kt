package com.wassimbh.projectdaggerretrofitmvvm.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class AttacksList (
    @SerializedName("attacks")
    var attacksList: List<Attacks>
){
    override fun toString(): String {
        return "AttacksList(attacksList=$attacksList)"
    }
    fun getList():List<Attacks>{
        return this.attacksList
    }
}