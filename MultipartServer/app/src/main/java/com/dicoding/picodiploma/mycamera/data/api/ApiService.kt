package com.dicoding.picodiploma.mycamera.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    //menagani contract endpoint
    @Multipart
    @POST("stories/guest")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): FileUploadResponse
}