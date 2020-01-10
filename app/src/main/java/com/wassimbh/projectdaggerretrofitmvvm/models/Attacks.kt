package com.wassimbh.projectdaggerretrofitmvvm.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Attacks (
    @Expose
    @PrimaryKey
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("pokemone_id")
    var pokemone_id: String,
    @Expose
    @SerializedName("cost")
    var cost: Array<String>,
    @Expose
    @SerializedName("text")
    var text: String,
    @Expose
    @SerializedName("damage")
    var damage: String,
    @Expose
    @SerializedName("convertedEnergyCost")
    var convertedEnergyCost: Int
){
    override fun toString(): String {
        return "pokemone_id='$pokemone_id', cost=$cost, name='$name', text='$text', damage='$damage', convertedEnergyCost=$convertedEnergyCost)"
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}