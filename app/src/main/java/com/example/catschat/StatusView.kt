package com.example.catschat

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.View
import com.example.domain.Status

class StatusView(context: Context, attributeSet: AttributeSet) : View(
    context,
    attributeSet
) {
    var status = Status.OFFLINE
        set(value) {
            field = value
            invalidate()
        }
    private val paint = Paint()

    init {
        paint.style = Style.FILL
        paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width / 2f
        when (status) {
            Status.ONLINE -> {
                paint.color = Color.GREEN
            }

            Status.OFFLINE -> {
                paint.color = Color.RED
            }
        }
        canvas.drawCircle(centerX, centerY, radius, paint)
    }
}