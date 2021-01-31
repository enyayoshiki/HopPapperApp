package com.example.hotpapperapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope

class ViewModelFactory(
    private val coroutineScope: CoroutineScope
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            coroutineScope) as T
    }
}

class ResearchViewModelFactory(
    private val coroutineScope: CoroutineScope
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResearchViewModel(
            coroutineScope) as T
    }
}