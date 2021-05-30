package com.example.walley.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walley.data.FetchPhotosRepository
import com.example.walley.model.Photo
import com.example.walley.model.PhotosBody
import com.google.gson.Gson

class PhotosViewModel(private val photosRepository: FetchPhotosRepository) : ViewModel() {

    private val _photoList = MutableLiveData<List<Photo>>()
    val photoList : LiveData<List<Photo>>
        get() = _photoList

    private val _photoFetchStatus = MutableLiveData<PhotosFetchStatus>()
    val photoFetchStatus : LiveData<PhotosFetchStatus>
        get() = _photoFetchStatus

    init {
        _photoFetchStatus.value = PhotosFetchStatus.FETCHING

        photosRepository.fetchCuratedPhotos(
            onSuccess = { response ->
                convertJsonResponseIntoPhotoList(jsonResponse = response)
                _photoFetchStatus.value = PhotosFetchStatus.FETCHED
            },

            onFailure = {
                _photoFetchStatus.value = PhotosFetchStatus.FAILURE
            }
        )
    }

    private fun convertJsonResponseIntoPhotoList(jsonResponse: String) {
        val photosBody : PhotosBody = Gson().fromJson(jsonResponse, PhotosBody::class.java) ?: return
        _photoList.value = photosBody.photos
    }

    class Factory(
        private val photosRepository: FetchPhotosRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PhotosViewModel(photosRepository) as T
        }

    }
}

enum class PhotosFetchStatus{
    FETCHING,
    FETCHED,
    FAILURE
}