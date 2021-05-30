package com.example.walley.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class FetchPhotosRepository(context : Context) {

    private val BASE_URL = "https://api.pexels.com/v1/"
    private val CURATED_PHOTOS_URL = "curated/"

    private val API_KEY = "563492ad6f91700001000001e6b41b62369e497a8cf98acfc5521947"

    private val volleyQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }

    fun fetchCuratedPhotos(onSuccess : (String) -> Unit, onFailure : () -> Unit){
        val stringRequest : StringRequest = object : StringRequest(Method.GET, BASE_URL + CURATED_PHOTOS_URL, { response ->
            onSuccess(response)
        } , {
            onFailure()
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers.put("Authorization", API_KEY)
                return headers
            }
        }

        volleyQueue.add(stringRequest)
    }
}