package com.wassimbh.projectdaggerretrofitmvvm.ui.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.databinding.FragmentDetailBinding
import com.wassimbh.projectdaggerretrofitmvvm.injection.ViewModelFactory
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.utils.eventbus.PokemonBus
import kotlinx.android.synthetic.main.fragment_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class DetailFragment: Fragment() {
    lateinit var pokemon: Pokemon
    lateinit var attacks: Attacks
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailBinding
    lateinit var myContext: Context
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.myRecyclerView.layoutManager = LinearLayoutManager(myContext)
        binding.myRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(DetailsViewModel::class.java)
        viewModel.onRetrieveAttacksListStart()
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        Picasso
            .get()
            .load(pokemon.imageUrl)
            .into(binding.pokeImg)
        binding.pokeName.text = "Name: "+pokemon.name
        binding.viewModel = viewModel
        showAttacks()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(pokemon: Pokemon) = DetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("pokemon", pokemon )
            }
        }
    }

    fun showAttacks(){
        viewModel.attacksList(pokemon.id).observe(this, Observer{list->
            if(list.isNotEmpty()){
                viewModel.onRetrieveAttakcsListFinish()
                viewModel.attacksMutableLiveData.value = list
                viewModel.onRetrieveAttakcsListSuccess()
            }
            else{
                viewModel.onRetrieveAttakcsListFinish()
                viewModel.onRetrieveAttacksListError(R.string.list_attacks_empty)
            }
        })
    }
    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(detailFragmentConstraint, errorMessage, Snackbar.LENGTH_SHORT)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = activity!!
        pokemon = arguments?.getSerializable("pokemon") as Pokemon
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventFired(event: PokemonBus){
        Toast.makeText(myContext,  event.pokemon.name+" - " + event.eventFrom, Toast.LENGTH_SHORT).show()
    }
}
