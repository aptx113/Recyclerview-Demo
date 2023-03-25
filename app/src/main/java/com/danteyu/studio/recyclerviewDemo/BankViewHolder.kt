package com.danteyu.studio.recyclerviewDemo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.danteyu.studio.recyclerviewDemo.databinding.ItemBankBinding
import com.danteyu.studio.recyclerviewDemo.model.Bank

class BankViewHolder(private val viewDataBinding: ItemBankBinding) :
    ViewHolder(viewDataBinding.root), LifecycleOwner {
    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }

    fun bind(item: Bank, viewModel: MainViewModel) {
        viewDataBinding.apply {
            lifecycleOwner = this@BankViewHolder
            this.viewModel = viewModel
            bank = item
            executePendingBindings()
        }
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    fun onAttach() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    fun onDetach() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }
}
