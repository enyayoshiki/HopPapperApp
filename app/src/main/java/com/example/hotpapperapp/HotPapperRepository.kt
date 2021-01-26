package com.example.hotpapperapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HotPapperRepository {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    private val retrofit = Retrofit.Builder()
//        .client(OkHttpClient.Builder().apply {
//            if (BuildConfig.DEBUG)
//                addInterceptor(HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BODY
//                })
//        }
//            .build())
        .baseUrl("http://webservice.recruit.co.jp")
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()

    private val api = retrofit.create(HotPapperApi::class.java)

    suspend fun fetchPublicTimeline() = withContext(Dispatchers.IO) {
        api.getGourmetListTest()
    }
}
