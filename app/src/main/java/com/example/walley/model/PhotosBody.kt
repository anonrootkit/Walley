package com.example.walley.model

import com.google.gson.annotations.SerializedName

data class PhotosBody(
    @SerializedName("photos")
    val photos : List<Photo>
)