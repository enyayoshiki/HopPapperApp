package com.example.hotpapperapp

import retrofit2.http.GET
import retrofit2.http.Query

interface HotPapperApi  {
    // hotpepper/gourmet/v1
    // ?key=530e8b6a0cbf2e93&large_area=Z011&format=json
    @GET("hotpepper/gourmet/v1")
//    fun fetchPublicTimelin(): Call<ResponseBody>
    suspend fun fetchPublicTimeline(

    ):GourmetResponse

    @GET("hotpepper/gourmet/v1")
    suspend fun getGourmetListTest(
        @Query("large_area") largeArea: String = "Z011",
        @Query("key") key: String = "530e8b6a0cbf2e93",
        @Query("format") format: String = "json"
    ):GourmetResponse

    @GET("hotpepper/gourmet/v1")
    suspend fun getGourmetListFromLocation(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("range") range: Int, // 1: 300m, 2: 500m, 3: 1km, 4: 2km, 5: 3km
        @Query("key") key: String = "530e8b6a0cbf2e93",
        @Query("format") format: String = "json"
    ) :GourmetResponse
}