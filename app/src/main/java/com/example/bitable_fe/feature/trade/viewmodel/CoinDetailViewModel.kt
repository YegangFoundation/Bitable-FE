package com.example.bitable_fe.feature.trade.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(

): ViewModel(){
    var period by mutableIntStateOf(1)

    fun setPeriodTab(index: Int){
        period = index
        // TODO("내역 변경")
    }
}