package com.example.mywidgets

import java.util.Random

//ini adalah kelas helper untuk membuat angka random
internal object NumberGenerator {
    fun generate(max : Int) : Int{
        val random = Random()
        return random.nextInt(max)
    }
}