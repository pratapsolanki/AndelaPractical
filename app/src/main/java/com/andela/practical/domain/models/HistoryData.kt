package com.andela.practical.domain.models

data class HistoryData(
    val base: String?,
    val end_date: String?,
    val rates: HashMap<String, HashMap<String , String>>,
    val start_date: String?,
    val success: Boolean?,
    val timeseries: Boolean?
)