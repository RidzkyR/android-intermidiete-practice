package com.dicoding.picodiploma.loginwithanimation.data.pref

//data untuk user
data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)