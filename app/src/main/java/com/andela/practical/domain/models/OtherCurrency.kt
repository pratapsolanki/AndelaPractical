package com.andela.practical.domain.models

data class OtherCurrency(
    val quotes: HashMap<String , Double>,
    val source: String?,
    val success: Boolean?,
    val timestamp: Int?
)