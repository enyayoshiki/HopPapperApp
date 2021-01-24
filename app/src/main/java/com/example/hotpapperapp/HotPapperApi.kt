package com.example.hotpapperapp

import retrofit2.http.GET
import retrofit2.http.Query

interface HotPapperApi  {
    @GET("?key=530e8b6a0cbf2e93&large_area=Z011")
//    fun fetchPublicTimelin(): Call<ResponseBody>
    suspend fun fetchPublicTimeline(
        @Query("max_id") maxId: String? = null,
        @Query("only_media") onlyMedia: Boolean = false
    ):List<Shop>
}