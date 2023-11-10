package com.example.apprf.data.remote.model

import com.google.gson.annotations.SerializedName

data class GameDetailDto(
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("description")
    var longDesc: String? = null,
    @SerializedName("Seller")
    var seller: String? = null,
    @SerializedName("ubication")
    var ubication: String? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("video")
    var video: String? = null,
    @SerializedName("longitud")
    var longitud: Double? = null,
    @SerializedName("latitud")
    var latitud: Double? = null

)
