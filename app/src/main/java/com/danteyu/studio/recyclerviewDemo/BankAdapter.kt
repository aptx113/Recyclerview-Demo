package com.danteyu.studio.recyclerviewDemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.danteyu.studio.recyclerviewDemo.databinding.ItemBankBinding
import com.danteyu.studio.recyclerviewDemo.model.Bank

class BankAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Bank, BankViewHolder>(BankDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBankBinding.inflate(layoutInflater, parent, false)
        return BankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    override fun onViewAttachedToWindow(holder: BankViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: BankViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

    class BankDiffUtil : DiffUtil.ItemCallback<Bank>() {
        override fun areItemsTheSame(oldItem: Bank, newItem: Bank): Boolean {
            return oldItem.bankCode == newItem.bankCode
        }

        override fun areContentsTheSame(oldItem: Bank, newItem: Bank): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }
    }
}