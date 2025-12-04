package com.example.bitable_fe.core.data.model

data class CoinItem(
    val name: String,
    val pair: String,
    val price: String,
    val changeRate: String,
    val isPositive: Boolean
)