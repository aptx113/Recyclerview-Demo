package com.danteyu.studio.recyclerviewDemo.model

data class Bank(
    val bankCode: String,
    val bankName: String,
    val isSelected: Boolean = false,
    val enable: Boolean = true
)
