package com.danteyu.studio.recyclerviewDemo.data

import com.danteyu.studio.recyclerviewDemo.model.Bank

val mockBanks by lazy {
    listOf(
        Bank("TSIB", "台新銀行"),
        Bank("SINO", "永豐銀行"),
        Bank("CTBC", "中國信託"),
        Bank("ESUN", "玉山銀行"),
        Bank("FCBK", "第一銀行")
    )
}

val mockApiData by lazy {
    listOf("TSIB", "SINO")
}
