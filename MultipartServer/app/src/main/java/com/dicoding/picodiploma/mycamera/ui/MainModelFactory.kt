package com.dicoding.picodiploma.mycamera.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.mycamera.data.UploadRepository
import com.dicoding.picodiploma.mycamera.data.di.Injection

class MainModelFactory private constructor(private val uploadRepository: UploadRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(uploadRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: MainViewModel? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MainViewModel(Injection.provideRepository())
            }.also { instance = it }
    }
}