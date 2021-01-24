package com.example.hotpapperapp
import com.squareup.moshi.Json


data class GourmetResponse(
    @Json(name = "results")
    var results: Results
)

data class Results(
    @Json(name = "api_version")
    var apiVersion: String,
    @Json(name = "results_available")
    var resultsAvailable: Int,
    @Json(name = "results_returned")
    var resultsReturned: String,
    @Json(name = "results_start")
    var resultsStart: Int,
    @Json(name = "shop")
    var shop: List<Shop>
)

data class Shop(
    @Json(name = "access")
    var access: String,
    @Json(name = "address")
    var address: String,
    @Json(name = "band")
    var band: String,
    @Json(name = "barrier_free")
    var barrierFree: String,
    @Json(name = "budget")
    var budget: Budget,
    @Json(name = "budget_memo")
    var budgetMemo: String,
    @Json(name = "capacity")
    var capacity: Int,
    @Json(name = "card")
    var card: String,
    @Json(name = "catch")
    var `catch`: String,
    @Json(name = "charter")
    var charter: String,
    @Json(name = "child")
    var child: String,
    @Json(name = "close")
    var close: String,
    @Json(name = "coupon_urls")
    var couponUrls: CouponUrls,
    @Json(name = "course")
    var course: String,
    @Json(name = "english")
    var english: String,
    @Json(name = "free_drink")
    var freeDrink: String,
    @Json(name = "free_food")
    var freeFood: String,
    @Json(name = "genre")
    var genre: Genre,
    @Json(name = "horigotatsu")
    var horigotatsu: String,
    @Json(name = "id")
    var id: String,
    @Json(name = "karaoke")
    var karaoke: String,
    @Json(name = "ktai_coupon")
    var ktaiCoupon: Int,
    @Json(name = "large_area")
    var largeArea: LargeArea,
    @Json(name = "large_service_area")
    var largeServiceArea: LargeServiceArea,
    @Json(name = "lat")
    var lat: Double,
    @Json(name = "lng")
    var lng: Double,
    @Json(name = "logo_image")
    var logoImage: String,
    @Json(name = "lunch")
    var lunch: String,
    @Json(name = "middle_area")
    var middleArea: MiddleArea,
    @Json(name = "midnight")
    var midnight: String,
    @Json(name = "mobile_access")
    var mobileAccess: String,
    @Json(name = "name")
    var name: String,
    @Json(name = "name_kana")
    var nameKana: String,
    @Json(name = "non_smoking")
    var nonSmoking: String,
    @Json(name = "open")
    var `open`: String,
    @Json(name = "other_memo")
    var otherMemo: String,
    @Json(name = "parking")
    var parking: String,
    @Json(name = "party_capacity")
    var partyCapacity: Int,
    @Json(name = "pet")
    var pet: String,
    @Json(name = "photo")
    var photo: Photo,
    @Json(name = "private_room")
    var privateRoom: String,
    @Json(name = "service_area")
    var serviceArea: ServiceArea,
    @Json(name = "shop_detail_memo")
    var shopDetailMemo: String,
    @Json(name = "show")
    var show: String,
    @Json(name = "small_area")
    var smallArea: SmallArea,
    @Json(name = "station_name")
    var stationName: String,
    @Json(name = "sub_genre")
    var subGenre: SubGenre,
    @Json(name = "tatami")
    var tatami: String,
    @Json(name = "tv")
    var tv: String,
    @Json(name = "urls")
    var urls: Urls,
    @Json(name = "wedding")
    var wedding: String,
    @Json(name = "wifi")
    var wifi: String
)

data class Budget(
    @Json(name = "average")
    var average: String,
    @Json(name = "code")
    var code: String,
    @Json(name = "name")
    var name: String
)

data class CouponUrls(
    @Json(name = "pc")
    var pc: String,
    @Json(name = "sp")
    var sp: String
)

data class Genre(
    @Json(name = "catch")
    var `catch`: String,
    @Json(name = "code")
    var code: String,
    @Json(name = "name")
    var name: String
)

data class LargeArea(
    @Json(name = "code")
    var code: String,
    @Json(name = "name")
    var name: String
)

data class LargeServiceArea(
    @Json(name = "code")
    var code: String,
    @Json(name = "name")
    var name: String
)

data class MiddleArea(
    @Json(name = "code")
    var code: String,
    @Json(name = "name")
    var name: String
)

data class Photo(
    @Json(name = "mobile")
    var mobile: Mobile,
    @Json(name = "pc")
    var pc: Pc
)

data class ServiceArea(
    @Json(name = "code")
    var code: String,
    @Json(name = "name")
    var name: String
)

data class SmallArea(
    @Json(name = "code")
    var code: String,
    @Json(name = "name")
    var name: String
)

data class SubGenre(
    @Json(name = "code")
    var code: String,
    @Json(name = "name")
    var name: String
)

data class Urls(
    @Json(name = "pc")
    var pc: String
)

data class Mobile(
    @Json(name = "l")
    var l: String,
    @Json(name = "s")
    var s: String
)

data class Pc(
    @Json(name = "l")
    var l: String,
    @Json(name = "m")
    var m: String,
    @Json(name = "s")
    var s: String
)