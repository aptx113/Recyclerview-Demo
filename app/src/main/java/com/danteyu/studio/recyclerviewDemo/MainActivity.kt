/*
 * Copyright 2021 Dante Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.danteyu.studio.recyclerviewDemo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.danteyu.studio.recyclerviewDemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BankAdapter

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        setContentView(binding.root)
        setupRecyclerview()
        viewModel.getBanks()
        binding.testBtn.setOnClickListener {
            viewModel.mockAPI()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectBank()
                launch {
                    viewModel.enableUI.collect {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun setupRecyclerview() {
        adapter = BankAdapter(viewModel)
        binding.recyclerview.adapter = adapter
    }

    private fun CoroutineScope.collectBank() = launch {
        viewModel.bank.collect {
            Timber.i("finalBank: $it")
            adapter.submitList(it)
        }
    }
}
