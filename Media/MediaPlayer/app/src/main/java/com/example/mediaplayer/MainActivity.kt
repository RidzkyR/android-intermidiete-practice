package com.example.mediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnPlay = findViewById<Button>(R.id.btn_play)
        val btnStop = findViewById<Button>(R.id.btn_stop)
        btnPlay.setOnClickListener {
            if (!isReady) {
                mediaPlayer?.prepareAsync()
            } else {
                if (mediaPlayer?.isPlaying as Boolean) {
                    mediaPlayer?.pause()
                } else {
                    mediaPlayer?.start()
                }
            }
        }
        btnStop.setOnClickListener {
            if (mediaPlayer?.isPlaying as Boolean || isReady) {
                mediaPlayer?.stop()
                isReady = false
                Log.d("TES","Media Player Stop")
            }
        }

        startMediaPlayer()

    }

    private fun startMediaPlayer() {
        mediaPlayer = MediaPlayer()
        // konfigurasi AudioAttributes
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mediaPlayer?.setAudioAttributes(attribute)

        // assetFileDescriptor
        val afd = applicationContext.resources.openRawResourceFd(R.raw.music)
        try {
            mediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaPlayer?.setOnPreparedListener {
            isReady = true
            mediaPlayer?.start()
            Log.d("TES","Media Player run.....")
        }
        mediaPlayer?.setOnErrorListener { mp, what, extra ->
            false
        }
    }
}