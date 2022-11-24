package com.andela.practical.domain.models

data class History(var data: String, var list: ArrayList<Currency>)
data class Currency(var key: String, var value: Double)