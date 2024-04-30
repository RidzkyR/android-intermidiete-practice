package com.example.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MyButton: AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}