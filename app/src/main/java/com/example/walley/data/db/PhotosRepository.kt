package com.example.walley.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.walley.model.Photo
import com.example.walley.model.PhotoURL

class PhotosRepository(private val photosDao: PhotosDao) {

    val photos : LiveData<List<Photo>> = Transformations.map(photosDao.getPhotosFromDB()) { photoEntityList ->
        photoEntityList?.map { photoEntity ->
            Photo(
                photoId = photoEntity.photoId,
                photographer = photoEntity.photographerName,
                photoUrl = PhotoURL(photoEntity.photoUrl)
            )
        } ?: ArrayList()
    }

    suspend fun insertPhotoEntities(photoList : List<Photo>) {
        val photoEntityList : List<PhotoEntity> = photoList.map { photo ->
            PhotoEntity(
                photoId = photo.photoId,
                photoUrl = photo.photoUrl.url,
                photographerName = photo.photographer
            )
        }

        photosDao.insertPhotoEntity(*photoEntityList.toTypedArray())
    }





}