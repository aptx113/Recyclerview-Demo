package com.danteyu.studio.recyclerviewDemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.recyclerviewDemo.data.mockApiData
import com.danteyu.studio.recyclerviewDemo.data.mockBanks
import com.danteyu.studio.recyclerviewDemo.model.Bank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _bank = MutableStateFlow<List<Bank>>(listOf())
    val bank = _bank.asStateFlow()

    private val _enableUI = MutableStateFlow(true)
    val enableUI = _enableUI.asStateFlow()

    fun getBanks() = viewModelScope.launch {
        _bank.update { mockBanks }
    }

    fun mockAPI() = viewModelScope.launch {
        _bank.value.forEach {
            itemEnableAction(it)
        }
        _enableUI.update { false }
        delay(1000)
        mockApiData.forEach { apiData ->
            val bank = _bank.value.filter { it.bankCode == apiData && !it.isSelected }
            bank.forEach { bankNeedUpdate ->
                itemCheckAction(bankNeedUpdate)

            }
        }
        _bank.value.forEach {
            itemEnableAction(it)
        }
        _enableUI.value = true
    }

    fun updateCheckBox(bankCode: String, isSelected: Boolean) {
        val updatedBank =
            _bank.value.find { it.bankCode == bankCode }?.copy(isSelected = !isSelected) ?: return
        val updatedList = _bank.value.toMutableList()
            .apply { replaceAll { if (it.bankCode == updatedBank.bankCode) updatedBank else it } }
        _bank.value = updatedList
    }

    val itemCheckAction: (Bank) -> Unit = { bank ->
        val newBank = bank.copy(isSelected = !bank.isSelected)
        updateCheckBox(bank, newBank)
    }

    fun updateCheckBox(currentBank: Bank, newBank: Bank) {
        val newList = _bank.value.map {
            if (it.bankCode == currentBank.bankCode) {
                newBank
            } else it
        }
        _bank.value = newList
    }

    val itemEnableAction: (Bank) -> Unit = { bank ->
        val newBank = bank.copy(enable = !bank.enable)
        updateCheckBox(bank, newBank)
    }
}
