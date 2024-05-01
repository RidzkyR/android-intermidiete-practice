package com.example.likesapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.likesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
    private val canvas = Canvas(bitmap)
    private val paint = Paint()

    private val halfOfWidth = (bitmap.width / 2).toFloat()
    private val halfOfHeight = (bitmap.height / 2).toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // inisialisai bitmap untuk canvas
        binding.imageView.setImageBitmap(bitmap)

        binding.like.setOnClickListener {
            bitmap.eraseColor(0) // menghapus gambar sebelumnya
            showFace()
            showEyes()
            showMouth(true)
            binding.imageView.setImageBitmap(bitmap) // panggil ulang tampilan untuk imageView
        }

        binding.dislike.setOnClickListener {
            bitmap.eraseColor(0) // menghapus gambar sebelumnya
            showFace()
            showEyes()
            showMouth(false)
            binding.imageView.setImageBitmap(bitmap) // panggil ulang tampilan untuk imageView
        }

    }

    private fun showFace() {
        val face = RectF(150F, 250F, bitmap.width - 105F, bitmap.height.toFloat() - 50F)

        // menggambar kepala bagian kiri
        paint.color =
            ResourcesCompat.getColor(resources, R.color.yellow_left_skin, null) // manggil warna
        canvas.drawArc(face, 90F, 180F, false, paint)

        // menggambar kepala bagian kanan
        paint.color =
            ResourcesCompat.getColor(resources, R.color.yellow_right_skin, null) // manggil warna
        canvas.drawArc(face, 270F, 180F, false, paint)
    }

    private fun showEyes() {
        // hati-hati penulisan baris kode berpengaruh terhadap hasil

        // bola mata
        paint.color = ResourcesCompat.getColor(resources, R.color.black, null)
        canvas.drawCircle(halfOfWidth - 80F, halfOfHeight - 10F, 50F, paint)
        canvas.drawCircle(halfOfWidth + 120F, halfOfHeight - 10F, 50F, paint)

        // hightlight mata
        paint.color = ResourcesCompat.getColor(resources, R.color.white, null)
        canvas.drawCircle(halfOfWidth - 105F, halfOfHeight - 20F, 15F, paint)
        canvas.drawCircle(halfOfWidth + 100F, halfOfHeight - 20F, 15F, paint)
    }

    private fun showMouth(isHappy: Boolean) {
        when (isHappy) {
            true -> {
                paint.color = ResourcesCompat.getColor(resources, R.color.black, null)
                val lip = RectF(
                    halfOfWidth - 160F,
                    halfOfHeight - 100F,
                    halfOfWidth + 200F,
                    halfOfHeight + 400F
                )
                canvas.drawArc(lip, 25F, 130F, false, paint)

                paint.color = ResourcesCompat.getColor(resources, R.color.white, null)
                val mouth =
                    RectF(halfOfWidth - 140F, halfOfHeight, halfOfWidth + 180F, halfOfHeight + 380F)
                canvas.drawArc(mouth, 25F, 130F, false, paint)
            }

            false -> {
                paint.color = ResourcesCompat.getColor(resources, R.color.black, null)
                val lip = RectF(
                    halfOfWidth - 160F,
                    halfOfHeight + 250F,
                    halfOfWidth + 200F,
                    halfOfHeight + 350F
                )
                canvas.drawArc(lip, 0F, -180F, false, paint)

                paint.color = ResourcesCompat.getColor(resources, R.color.white, null)
                val mouth = RectF(
                    halfOfWidth - 140F,
                    halfOfHeight + 260F,
                    halfOfWidth + 180F,
                    halfOfHeight + 330F
                )
                canvas.drawArc(mouth, 0F, -180F, false, paint)
            }
        }
    }
}