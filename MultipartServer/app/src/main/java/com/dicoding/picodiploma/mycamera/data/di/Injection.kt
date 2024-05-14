package com.dicoding.picodiploma.mycamera.data.di

import android.content.Context
import com.dicoding.picodiploma.mycamera.data.UploadRepository
import com.dicoding.picodiploma.mycamera.data.api.ApiConfig
import com.dicoding.picodiploma.mycamera.data.api.ApiService

object Injection {
    fun provideRepository(): UploadRepository{
        val apiService = ApiConfig.getApiService()
        return UploadRepository.getInstance(apiService)
    }
}