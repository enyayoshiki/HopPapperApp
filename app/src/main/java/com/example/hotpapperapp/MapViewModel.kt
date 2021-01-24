package com.example.hotpapperapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MapViewModel(
    instanceId: String,
    private val coroutineScope: CoroutineScope)
    : ViewModel(){

    private val repository = HotPapperRepository(instanceId)
    var isLoading = MutableLiveData<Boolean>(false)
    var itemList = MutableLiveData<MutableList<Shop>>()

    fun loadNext(){
        coroutineScope.launch {
            isLoading.postValue(true)

            val itemListSnapshot = itemList.value ?: mutableListOf()

            val itemListResponse = repository.fetchPublicTimeline()

            itemList.postValue(itemListResponse as MutableList<Shop>?)
            isLoading.postValue(false)
        }
    }
}
