package com.example.hotpapperapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope

class ViewModelFactory(
    private val instanceUrl: String,
    private val coroutineScope: CoroutineScope
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(
            instanceUrl,
            coroutineScope) as T
    }
}