package com.danteyu.studio.recyclerviewDemo

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.danteyu.studio.recyclerviewDemo.databinding.ItemBankBinding
import com.danteyu.studio.recyclerviewDemo.model.Bank

class BankViewHolder(private val viewDataBinding: ItemBankBinding,private val owner: LifecycleOwner) :
    ViewHolder(viewDataBinding.root) {


    fun bind(item: Bank, viewModel: MainViewModel) {
        viewDataBinding.apply {
            this.viewModel = viewModel
            lifecycleOwner = owner
            bank = item
            executePendingBindings()
        }
    }
}
