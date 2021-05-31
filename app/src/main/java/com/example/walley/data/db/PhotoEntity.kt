package com.example.walley.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    @ColumnInfo(name ="id")
    val photoId : Int,

    @ColumnInfo(name = "url")
    val photoUrl : String,

    @ColumnInfo(name = "photographer_name")
    val photographerName : String
)