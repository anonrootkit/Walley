package com.example.walley.data.network

import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.example.walley.data.db.PhotosRepository
import com.example.walley.model.PhotosBody
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FetchPhotosRepository(private val volleyQueue: RequestQueue, private val photosRepository: PhotosRepository) {

    private val job = Job()
    private val scope : CoroutineScope = CoroutineScope(job + Dispatchers.IO)

    private val BASE_URL = "https://api.pexels.com/v1/"
    private val CURATED_PHOTOS_URL = "curated/"

    private val API_KEY = "563492ad6f91700001000001e6b41b62369e497a8cf98acfc5521947"


    fun fetchCuratedPhotos(onFailure : () -> Unit){
        val stringRequest : StringRequest = object : StringRequest(Method.GET, BASE_URL + CURATED_PHOTOS_URL, { response ->
            scope.launch {
                convertJsonResponseIntoPhotoList(response)
            }
        } , {
            onFailure()
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers["Authorization"] = API_KEY
                return headers
            }
        }

        volleyQueue.add(stringRequest)
    }

    private suspend fun convertJsonResponseIntoPhotoList(jsonResponse: String) {
        val photosBody : PhotosBody = Gson().fromJson(jsonResponse, PhotosBody::class.java) ?: return
        photosRepository.insertPhotoEntities(photoList = photosBody.photos)
    }
}