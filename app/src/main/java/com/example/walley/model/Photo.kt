package com.example.walley.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    val photoId : Int,
    @SerializedName("src")
    val photoUrl : PhotoURL,
    @SerializedName("photographer")
    val photographer : String
)

data class PhotoURL(
    @SerializedName("original")
    val url : String
)