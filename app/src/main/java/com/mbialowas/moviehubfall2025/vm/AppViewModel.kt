package com.mbialowas.moviehubfall2025.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AppViewModel: ViewModel() {

    var counter = mutableStateOf(0)
        private set

    fun incrementCounter(){
        counter.value += 1
    }
}