package com.wassimbh.projectdaggerretrofitmvvm.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wassimbh.projectdaggerretrofitmvvm.R

/**
 * A simple [Fragment] subclass.
 */
class PokemonListFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view = inflater.inflate(R.layout.fragment_pokemon_list, container, false)


        return view
    }


}
