package com.wassimbh.projectdaggerretrofitmvvm.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.databinding.SingleAttackRowBinding
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.AttackViewModel

class AttacksAdapter: RecyclerView.Adapter<AttacksAdapter.ViewHolder>(){

    private lateinit var attacksList:List<Attacks>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleAttackRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.single_attack_row, parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val attack = attacksList[position]
        holder.bind(attack)
    }

    override fun getItemCount(): Int {
        return if(::attacksList.isInitialized) attacksList.size else 0
    }

    fun updateCardList(attacksList:List<Attacks>){
        this.attacksList = attacksList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: SingleAttackRowBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel =
            AttackViewModel()

        fun bind(attacks: Attacks){
            viewModel.bind(attacks)
            binding.viewModel = viewModel
        }
    }
}