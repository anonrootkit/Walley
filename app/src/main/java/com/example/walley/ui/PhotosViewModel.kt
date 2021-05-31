package com.example.walley.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walley.data.db.PhotosRepository
import com.example.walley.data.network.FetchPhotosRepository
import com.example.walley.model.Photo

class PhotosViewModel(
    fetchPhotosRepository: FetchPhotosRepository,
    photosRepository: PhotosRepository
) : ViewModel() {

    val photoList: LiveData<List<Photo>> = photosRepository.photos

//    private val _photoFetchStatus = MutableLiveData<PhotosFetchStatus>()
//    val photoFetchStatus : LiveData<PhotosFetchStatus>
//        get() = _photoFetchStatus

    init {
        fetchPhotosRepository.fetchCuratedPhotos(
            onFailure = {
//                _photoFetchStatus.value = PhotosFetchStatus.FAILURE
            }
        )
    }


    class Factory(
        private val fetchPhotosRepository: FetchPhotosRepository,
        private val photosRepository: PhotosRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PhotosViewModel(fetchPhotosRepository, photosRepository) as T
        }

    }
}

enum class PhotosFetchStatus {
    FETCHING,
    FETCHED,
    FAILURE
}