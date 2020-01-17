package com.wassimbh.projectdaggerretrofitmvvm.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.databinding.SingleCardModelLayoutBinding
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.PokemoneViewModel
import com.wassimbh.projectdaggerretrofitmvvm.utils.eventbus.PokemonBus
import org.greenrobot.eventbus.EventBus

class CardsRecyclerViewAdapter: RecyclerView.Adapter<CardsRecyclerViewAdapter.ViewHolder>() {
        private lateinit var pokemonList:List<Pokemon>
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: SingleCardModelLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.single_card_model_layout, parent, false)
            return ViewHolder(
                binding
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val pokemon = pokemonList[position]
            holder.bind(pokemon)
            holder.binding.img.setOnClickListener(View.OnClickListener {
                EventBus.getDefault().post(
                    PokemonBus(
                        pokemon,
                        "image"
                    )
                )
            })
        }

        override fun getItemCount(): Int {
            return if(::pokemonList.isInitialized) pokemonList.size else 0
        }

        fun updateCardList(pokemonList:List<Pokemon>){
            this.pokemonList = pokemonList
            notifyDataSetChanged()
        }

        class ViewHolder(val binding: SingleCardModelLayoutBinding):RecyclerView.ViewHolder(binding.root){
            private val viewModel =
                PokemoneViewModel()

            fun bind(pokemon: Pokemon){
                viewModel.bind(pokemon)
                binding.viewmodel = viewModel
            }
        }
    }