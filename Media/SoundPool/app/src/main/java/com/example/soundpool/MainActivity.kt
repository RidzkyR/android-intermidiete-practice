package com.example.soundpool

import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var soundId = 0
    private var soundLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn = findViewById<Button>(R.id.btn_sound_pool)

        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        soundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0){
                soundLoaded = true
            }else{
                Toast.makeText(this@MainActivity, "GAGAL LOAD",Toast.LENGTH_SHORT).show()
            }
        }

        soundId = soundPool.load(this,R.raw.fart,1)

        btn.setOnClickListener {
            if (soundLoaded){
                soundPool.play(soundId,1f,1f,0,0,1f)
            }
        }
    }
}