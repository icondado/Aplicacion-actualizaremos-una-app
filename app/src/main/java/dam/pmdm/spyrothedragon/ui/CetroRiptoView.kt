package dam.pmdm.spyrothedragon.ui

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CetroRiptoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paintBrillo = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.FILL
        // Aplicación de filtro de máscara para simular el resplandor de energía mágica
        maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.NORMAL)
    }

    private var radioBrillo = 20f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Renderizado del círculo de energía en el centro de la vista
        canvas.drawCircle(width / 2f, height / 2f, radioBrillo, paintBrillo)

        // Lógica de animación: incremento incremental del radio hasta el límite definido
        if (radioBrillo < 200f) {
            radioBrillo += 5f
            // Invalida la vista para solicitar un nuevo frame de dibujo (bucle de animación)
            invalidate()
        }
    }
}