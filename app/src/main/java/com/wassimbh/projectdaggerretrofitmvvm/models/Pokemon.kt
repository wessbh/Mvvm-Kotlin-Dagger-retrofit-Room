package com.wassimbh.projectdaggerretrofitmvvm.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
 data class Pokemon (
    @PrimaryKey
    @Expose
    @SerializedName("id")
    var id: String,
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("nationalPokedexNumber")
    var nationalPokedexNumber: String,
    @Expose
    @SerializedName("imageUrl")
    var imageUrl: String,
    @Expose
    @SerializedName("imageUrlHiRes")
    var imageUrlHiRes: String,
    @Expose
    @SerializedName("types")
    var types: Array<String>,
    @Ignore
    @SerializedName("attacks")
    var attacks: List<Attacks>
){

   constructor(): this("", "", "", "", "", arrayOf<String>(), listOf<Attacks>() )


   override fun equals(other: Any?): Boolean {
      return super.equals(other)
   }
   override fun hashCode(): Int {
      return super.hashCode()
   }

   override fun toString(): String {
      return "Pokemon(id='$id', name='$name', nationalPokedexNumber='$nationalPokedexNumber', imageUrl='$imageUrl', imageUrlHiRes='$imageUrlHiRes', types=${types.contentToString()}, attacks=$attacks)"
   }
}