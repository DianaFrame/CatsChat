package com.example.catschat

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class StatusView(context: Context, attributeSet: AttributeSet) : View(
    context,
    attributeSet
) {
    private val paint = Paint()

    init {
        paint.style = Style.FILL
        paint.color = ContextCompat.getColor(context, R.color.status_color)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width / 2f
        canvas.drawCircle(centerX, centerY, radius, paint)
    }
}