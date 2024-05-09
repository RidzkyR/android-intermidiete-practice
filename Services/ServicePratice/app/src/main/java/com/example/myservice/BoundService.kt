package com.example.myservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BoundService : Service() {
    private var binder = MyBinder()

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    val numberLiveData: MutableLiveData<Int> = MutableLiveData()

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: ")
        serviceScope.launch {
            for (i in 1..50) {
                delay(1000)
                Log.d(TAG, "Do Something $i")
                numberLiveData.postValue(i)
            }
            Log.d(TAG, "Service dihentikan")
        }
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind: ")
        serviceJob.cancel()
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }

    companion object {
        private val TAG = BoundService::class.java.simpleName
    }

    internal inner class MyBinder : Binder() {
        val getService: BoundService = this@BoundService
    }
}