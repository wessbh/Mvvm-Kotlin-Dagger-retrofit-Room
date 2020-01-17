package com.wassimbh.projectdaggerretrofitmvvm.ui.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.databinding.FragmentPokemonListBinding
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.ui.activities.MainActivity
import com.wassimbh.projectdaggerretrofitmvvm.ui.activities.MainActivityViewModel
import com.wassimbh.projectdaggerretrofitmvvm.utils.adapters.CardsRecyclerViewAdapter
import com.wassimbh.projectdaggerretrofitmvvm.utils.eventbus.PokemonBus
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class PokemonListFragment : Fragment() {
    lateinit var pokemon: Pokemon
    lateinit var type: String
    lateinit var pokemonListAdapter: CardsRecyclerViewAdapter
    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var viewModel: MainActivityViewModel
    private var errorSnackbar: Snackbar? = null
    lateinit var myActivity: MainActivity

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        pokemonListAdapter = CardsRecyclerViewAdapter()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_list, container, false)
        binding.myRecyclerView.layoutManager = GridLayoutManager(myActivity.applicationContext, 3)
        binding.myRecyclerView.setHasFixedSize(true)
        binding.myRecyclerView.adapter = pokemonListAdapter
        val activity = activity!! as MainActivity
        viewModel = activity.viewModel
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

        showPoke()

        return binding.root
    }

    private fun showPoke(){
        if(myActivity.checkNetworkState(myActivity.applicationContext)){
            viewModel.cardsListType(type).observe(this, Observer {list->
                if(list.isNotEmpty()){
                    viewModel.onRetrieveCardsListFinish()
                    viewModel.loadingVisibility.value = View.GONE
                    viewModel.pokemonMutableLiveData.value = list
                    pokemonListAdapter.updateCardList(list)
                }
                else{
                    viewModel.onRetrieveCardsListFinish()
                    viewModel.loadingVisibility.value = View.GONE
                    viewModel.onRetrieveCardsListError(R.string.my_error)
                }
            })
        }
        else{
            viewModel.cardsListTypeOffline(type).observe(this, Observer {list->
                if(list.isNotEmpty()){
                    viewModel.onRetrieveCardsListFinish()
                    viewModel.loadingVisibility.value = View.GONE
                    viewModel.pokemonMutableLiveData.value = list
                    pokemonListAdapter.updateCardList(list)
                }
                else{
                    viewModel.onRetrieveCardsListFinish()
                    viewModel.loadingVisibility.value = View.GONE
                    viewModel.onRetrieveCardsListError(R.string.my_error)
                }
            })
        }
    }
    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(listFragmentLayout, errorMessage, Snackbar.LENGTH_SHORT)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = activity!! as MainActivity
        type = arguments?.getString("type") as String
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventFired(event: PokemonBus){
        pokemon = event.pokemon
       val detailFragment: DetailFragment = DetailFragment.newInstance(event.pokemon)
        myActivity.changeFragment(detailFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) = PokemonListFragment().apply {
            arguments = Bundle().apply {
                putString("type", type)
            }
        }
    }


}
