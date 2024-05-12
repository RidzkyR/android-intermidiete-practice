package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

class MyButton : AppCompatButton {
    constructor(context: Context) : super(context) // untuk di Activity/Fragment
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) // untuk di XML

    private var textColor: Int = 0
    private var enableBackground: Drawable
    private var disableBackground: Drawable

    init {
        textColor = ContextCompat.getColor(context, android.R.color.background_light)
        enableBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        disableBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enableBackground else disableBackground
        setTextColor(textColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = if (isEnabled) "Submit" else "Isi dulu"
    }
}