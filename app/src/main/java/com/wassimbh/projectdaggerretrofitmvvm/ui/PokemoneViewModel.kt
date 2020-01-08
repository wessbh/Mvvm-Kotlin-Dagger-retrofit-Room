package com.wassimbh.projectdaggerretrofitmvvm.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import com.wassimbh.projectdaggerretrofitmvvm.base.BaseViewModel
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon

public class PokemoneViewModel: BaseViewModel() {
    private val pokemonName = MutableLiveData<String>()
    private val pokemonType = MutableLiveData<String>()
    private var pokemonImagUrl:String = ""

    fun bind(pokemon: Pokemon){
        pokemonName.value = pokemon.name
        pokemonType.value = pokemon.types[0]
        pokemonImagUrl = pokemon.imageUrl
    }

    fun getPokemonName(): MutableLiveData<String>{
        return pokemonName
    }
    fun getPokemonType(): MutableLiveData<String>{
        return pokemonType
    }
    fun getPokemonImageUrl(): String{
        return pokemonImagUrl
    }

    companion object{
        @JvmStatic
        @BindingAdapter("bind:imgUrl")
        fun loadImage(view: ImageView, imageUrl: String) {
            if (!imageUrl.isEmpty()) {
                Picasso
                    .get()
                    .load(imageUrl)
                    .into(view)
            }
        }
    }
}