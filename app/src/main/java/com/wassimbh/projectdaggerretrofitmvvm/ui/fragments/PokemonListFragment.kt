package com.wassimbh.projectdaggerretrofitmvvm.ui.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.databinding.FragmentPokemonListBinding
import com.wassimbh.projectdaggerretrofitmvvm.injection.ViewModelFactory
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.ui.activities.MainActivity
import com.wassimbh.projectdaggerretrofitmvvm.utils.eventbus.PokemonBus
import com.wassimbh.projectdaggerretrofitmvvm.utils.listeners.CustomRVItemTouchListener
import com.wassimbh.projectdaggerretrofitmvvm.utils.listeners.RecyclerViewItemClickListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class PokemonListFragment : Fragment() {
    lateinit var pokemon: Pokemon
    lateinit var myContext: Context
    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var viewModel: FragmentsViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon_list, container, false)
        binding = DataBindingUtil.setContentView(activity!!, R.layout.fragment_pokemon_list)
        binding.myRecyclerView.layoutManager = GridLayoutManager(myContext, 2)
        binding.myRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(FragmentsViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.myRecyclerView.addOnItemTouchListener(CustomRVItemTouchListener(activity!!, binding.myRecyclerView, object :
            RecyclerViewItemClickListener {
            override fun onClick(view: View, position: Int) {
                val myActivity: MainActivity = activity!! as MainActivity
                val detailFragment: DetailFragment = DetailFragment.newInstance(Pokemon())
                myActivity.changeFragment(detailFragment)
            }

            override fun onLongClick(view: View, position: Int) {
            }
        }))
        binding.viewModel = viewModel
        showPoke()

        return view
    }

    private fun showPoke(){
        try {
            viewModel.cardsList.observe(this, Observer {list->
                if(list.isNotEmpty()){
                    viewModel.onRetrieveCardsListFinish()
                    viewModel.pokemonMutableLiveData.value = list
                    viewModel.onRetrieveCardsListSuccess()
                }
                else
                    viewModel.onRetrieveCardsListError()
            })
        }
        catch (e: Throwable){
            viewModel.onRetrieveCardsListError()
        }
    }
    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    @Override
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Override
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = activity!!
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventFired(event: PokemonBus){
        Log.d("mriGel?!", event.pokemon.toString())
        pokemon = event.pokemon
       /* val detailFragment: DetailFragment = DetailFragment.newInstance(event.pokemon)
        Log.d("mriGel?!", event.pokemon.toString())*/
       // val myActivity: MainActivity = activity!! as MainActivity
       // myActivity.changeFragment(detailFragment)
        /*val transaction: FragmentTransaction =childFragmentManager.beginTransaction()
        transaction.add(R.id.framelayout2, detailFragment)
        transaction.addToBackStack(null)
        transaction.commit()*/
    }

}
