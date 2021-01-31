package com.example.hotpapperapp

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ResearchViewModel (private val coroutineScope: CoroutineScope)
: ViewModel(){

    private val repository = HotPapperRepository()
    var isLoading = MutableLiveData<Boolean>(false)
    var itemList = MutableLiveData<List<Shop>>()

    var keyword = MutableLiveData<String>().apply {
        value = ""
    }

    val isCreateReserchFragment = MediatorLiveData<Boolean>().also { result ->
        result.addSource(keyword) { result.value = getBoolean() }
    }

    fun researchKeyWord(){
        coroutineScope.launch {
            isLoading.postValue(true)

            val shops = repository.fetchPublicTimeline().results.shop
            itemList.postValue(shops)
            isLoading.postValue(false)
        }
    }

    private fun getBoolean(): Boolean {
        return keyword.value?.isNotEmpty() == true
    }
}