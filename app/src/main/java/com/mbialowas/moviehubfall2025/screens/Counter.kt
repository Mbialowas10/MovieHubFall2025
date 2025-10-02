package com.mbialowas.moviehubfall2025.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbialowas.moviehubfall2025.vm.AppViewModel

@Composable
fun Counter(viewModel: AppViewModel, modifier: Modifier = Modifier){
    var counter by remember { viewModel.counter }

    Button(
        modifier = modifier,
        onClick = {
            viewModel.incrementCounter()
        }
    ){
        Text(text="This button has been clicked $counter times.")
    }
}