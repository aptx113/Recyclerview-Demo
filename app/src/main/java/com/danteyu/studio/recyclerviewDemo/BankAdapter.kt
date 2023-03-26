package com.danteyu.studio.recyclerviewDemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.danteyu.studio.recyclerviewDemo.databinding.ItemBankBinding
import com.danteyu.studio.recyclerviewDemo.model.Bank

class BankAdapter(
    private val viewModel: MainViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    ListAdapter<Bank, BankViewHolder>(BankDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBankBinding.inflate(layoutInflater, parent, false)
        return BankViewHolder(binding, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class BankDiffUtil : DiffUtil.ItemCallback<Bank>() {
        override fun areItemsTheSame(oldItem: Bank, newItem: Bank): Boolean {
            return oldItem.bankCode == newItem.bankCode
        }

        override fun areContentsTheSame(oldItem: Bank, newItem: Bank): Boolean {
            return oldItem.isSelected == newItem.isSelected && oldItem.enable == newItem.enable
        }
    }
}