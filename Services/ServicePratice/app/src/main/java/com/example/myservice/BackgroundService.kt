package com.example.myservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BackgroundService : Service() {

    private val servicejob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + servicejob)
    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet Implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d(TAG, "Service running.....")
        serviceScope.launch {
            for (i in 1..10) {
                delay(1000)
                Log.d(TAG, "interation - $i")
            }
            stopSelf()
            Log.d(TAG, "Service Done...")
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        servicejob.cancel()
        serviceScope.cancel()
        Log.d(TAG,"onDestroy = Service Stopped")
    }

    companion object {
        internal val TAG = BackgroundService::class.java.simpleName
    }

}