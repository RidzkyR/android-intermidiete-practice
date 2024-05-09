package com.example.myservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForegroundService : Service() {

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
        Log.d(TAG, "onDestroy = Service Stopped")
    }

    private fun buildNotification(): Notification {
        // Memulai Intent untuk membuka MainActivity ketika notifikasi diklik
        val notificationIntent = Intent(this, MainActivity::class.java)

        // Konfigurasi flag untuk PendingIntent (bergantung pada versi Android)
        val pendingFlags: Int = if (Build.VERSION.SDK_INT >= 23) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Update notifikasi yang ada, dan flag untuk Android 23+
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT // Hanya update notifikasi yang ada
        }

        // Membuat PendingIntent yang akan dijalankan ketika notifikasi diklik
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, pendingFlags)

        // Mendapatkan referensi ke NotificationManager
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Memulai Notification Builder
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service") // Judul notifikasi
            .setContentText("Saat ini foreground service sedang berjalan.") // Isi notifikasi
            .setSmallIcon(R.mipmap.ic_launcher) // Ikon notifikasi (dari resource)
            .setContentIntent(pendingIntent) // Set apa yang terjadi ketika notifikasi diklik

        // Pengecekan untuk versi Android Oreo (8.0) dan yang lebih tinggi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel( // Membuat channel untuk notifikasi (wajib untuk Oreo+)
                    CHANNEL_ID, // ID channel (harus unik)
                    CHANNEL_NAME, // Nama channel
                    NotificationManager.IMPORTANCE_DEFAULT // Prioritas notifikasi
                )
            channel.description = CHANNEL_NAME // Deskripsi channel (opsional)
            notificationBuilder.setChannelId(CHANNEL_ID) // Set channel ID ke notification builder
            mNotificationManager.createNotificationChannel(channel) // Membuat channel di NotificationManager
        }

        // Membangun dan mengembalikan objek Notification
        return notificationBuilder.build()
    }

    companion object {
        internal val TAG = ForegroundService::class.java.simpleName

        private const val CHANNEL_ID = "channel_1"
        private const val CHANNEL_NAME = "main channel"
        private const val NOTIFICATION_ID = 1
    }
}