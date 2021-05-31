package com.example.walley.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhotoEntity(vararg photoEntity: PhotoEntity)

    @Query("SELECT * FROM photos")
    fun getPhotosFromDB() : LiveData<List<PhotoEntity>>

}