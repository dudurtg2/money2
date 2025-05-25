package com.tcc.money.ui.screens.login

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CurvedHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint = Paint().apply {
        color = Color.WHITE // cor da curva
        style = Paint.Style.FILL
        isAntiAlias = true
        setShadowLayer(20f, 0f, 10f, Color.argb(80, 0, 0, 0)) // sombra
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, paint) // necessário pra sombra funcionar
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val path = Path().apply {
            moveTo(0f, height * 0.6f)
            quadTo(width * 0.5f, height, width, height * 0.5f)
            lineTo(width, 0f)
            lineTo(0f, 0f)
            close()
        }

        canvas.drawPath(path, paint)
    }
}
