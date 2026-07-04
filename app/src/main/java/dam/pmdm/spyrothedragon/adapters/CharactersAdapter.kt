package dam.pmdm.spyrothedragon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import dam.pmdm.spyrothedragon.ui.CetroRiptoView
import dam.pmdm.spyrothedragon.R
import dam.pmdm.spyrothedragon.models.Character

class CharactersAdapter(
    private val list: List<Character>
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    private val characterImages = mapOf(
        "spyro" to R.drawable.spyro,
        "hunter" to R.drawable.hunter,
        "elora" to R.drawable.elora,
        "ripto" to R.drawable.ripto
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = list[position]
        holder.nameTextView.text = character.name

        val drawableRes = characterImages[character.image] ?: R.drawable.placeholder
        holder.imageImageView.setImageResource(drawableRes)

        //------------------------------------------------------------------------------------------->>>
        /**
         * Implementación del Easter Egg para el personaje "Ripto".
         * Se utiliza un LongClickListener para activar una animación personalizada basada en Canvas.
         */
        if (character.image == "ripto") {
            holder.itemView.setOnLongClickListener {
                // Instanciación de la vista personalizada que gestiona el dibujo de energía mágica
                val animacionMagica = CetroRiptoView(holder.itemView.context)

                // Acceso al contenedor raíz de la actividad para superponer la animación a toda la interfaz
                val rootLayout = holder.itemView.rootView.findViewById<ViewGroup>(android.R.id.content)

                // Configuración de parámetros de diseño para que la animación ocupe la pantalla completa
                val params = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                animacionMagica.layoutParams = params

                // Inserción dinámica de la vista en la jerarquía visual
                rootLayout.addView(animacionMagica)

                true // Consumo del evento de pulsación prolongada
            }
        } else {
            // Limpieza del listener en vistas recicladas para garantizar la integridad del comportamiento del RecyclerView
            holder.itemView.setOnLongClickListener(null)
        }
        //-------------------------------------------------------------------------------------------<<<
    }

    override fun getItemCount(): Int = list.size

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val imageImageView: ImageView = itemView.findViewById(R.id.image)
    }
}
