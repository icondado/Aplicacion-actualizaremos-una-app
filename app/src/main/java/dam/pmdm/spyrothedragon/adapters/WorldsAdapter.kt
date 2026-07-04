package dam.pmdm.spyrothedragon.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dam.pmdm.spyrothedragon.R
import dam.pmdm.spyrothedragon.ui.VideoPlayer
import dam.pmdm.spyrothedragon.models.World
import kotlin.jvm.java

class WorldsAdapter(
    private val list: List<World>
) : RecyclerView.Adapter<WorldsAdapter.WorldsViewHolder>() {

    private val worldImages = mapOf(
        "sunny_beach" to R.drawable.sunny_beach,
        "midday_gardens" to R.drawable.midday_gardens,
        "autumn_plains" to R.drawable.autumn_plains,
        "glimmer" to R.drawable.glimmer,
        "cloud_spires" to R.drawable.cloud_spires,
        "hurricane_halls" to R.drawable.hurricane_halls,
        "frozen_altars" to R.drawable.frozen_altars,
        "lost_fleet" to R.drawable.lost_fleet,
        "sunset_beach" to R.drawable.sunset_beach
    )

    //------------------------------------------------------------------------------------------->>>
    /**
     * Variables de estado para la gestión del Easter Egg.
     * Permiten rastrear la interacción repetitiva sobre un mismo elemento del listado.
     */
    private var clickCount = 0
    private var lastClickedPosition = -1
    //-------------------------------------------------------------------------------------------<<<

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)
        return WorldsViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorldsViewHolder, position: Int) {
        val world = list[position]
        holder.nameTextView.text = world.name

        val drawableRes = worldImages[world.image] ?: R.drawable.placeholder
        holder.imageImageView.setImageResource(drawableRes)

        //--------------------------------------------------------------------------------------->>>
        /**
         * Lógica de activación del Easter Egg de vídeo.
         * Se requiere la pulsación consecutiva (3 veces) sobre el mismo item para disparar
         * la actividad multimedia, cumpliendo con los requisitos de interacción oculta.
         */
        holder.itemView.setOnClickListener {
            // Verificación de consistencia: se comprueba si el clic actual es en la misma posición que el anterior
            if (lastClickedPosition == position) {
                clickCount++
            } else {
                // Si el usuario cambia de elemento, se reinicia el contador de clics y se actualiza la posición de referencia
                clickCount = 1
                lastClickedPosition = position
            }

            // Umbral de activación definido en el enunciado del proyecto
            if (clickCount == 3) {
                // Reseteo del contador para permitir futuras activaciones tras el cierre del vídeo
                clickCount = 0

                // Inicialización y lanzamiento de la actividad de reproducción multimedia
                val intent = Intent(holder.itemView.context, VideoPlayer::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }
        //---------------------------------------------------------------------------------------<<<
    }

    override fun getItemCount(): Int = list.size

    class WorldsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val imageImageView: ImageView = itemView.findViewById(R.id.image)
    }
}
