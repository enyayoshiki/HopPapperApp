package com.example.hotpapperapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MapViewModel(
    private val coroutineScope: CoroutineScope)
    : ViewModel(){

    private val repository = HotPapperRepository()
    var isLoading = MutableLiveData<Boolean>(false)
    var itemList = MutableLiveData<List<Shop>>()

    fun loadNext(){
        coroutineScope.launch {
            isLoading.postValue(true)

            val shops = repository.fetchPublicTimeline().results.shop
            itemList.postValue(shops)
            isLoading.postValue(false)
        }
    }
}
