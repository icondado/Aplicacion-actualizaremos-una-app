package dam.pmdm.spyrothedragon

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding
import androidx.core.content.edit
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    //------------------------------------------------------------------------------------------->>>
    // Gestión del flujo de la Guía de Usuario interactiva
    private var pasoActual = 1
    // Bloqueo de interacción
    private var guiaActiva = false
    //-------------------------------------------------------------------------------------------<<<

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.navHostFragment)

        navHostFragment?.let {
            navController = NavHostFragment.findNavController(it)
            NavigationUI.setupWithNavController(binding.navView, navController!!)
            NavigationUI.setupActionBarWithNavController(this, navController!!)
        }

        binding.navView.setOnItemSelectedListener { menuItem ->
            selectedBottomMenu(menuItem)
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_characters,
                R.id.navigation_worlds,
                R.id.navigation_collectibles -> {
                    // En las pantallas de los tabs no mostramos la flecha atrás
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                else -> {
                    // En el resto de pantallas sí
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }

        //--------------------------------------------------------------------------------------->>>
        /**
         * Lógica de inicialización de la Guía: Se verifica mediante SharedPreferences
         * si es la primera ejecución para mostrar el tutorial interactivo.
         */
        val prefs = getSharedPreferences("SpyroPrefs", Context.MODE_PRIVATE)
        if (prefs.getBoolean("firstRun", true)) {
            iniciarGuia()

            // Registro de la finalización del primer inicio
            prefs.edit().putBoolean("firstRun", false).apply()
        }
        //---------------------------------------------------------------------------------------<<<
    }

    //------------------------------------------------------------------------------------------->>>
    /**
     * Infla y superpone la vista de la guía sobre el contenedor principal.
     * Configura los listeners para la navegación entre pasos y la opción de omitir.
     */
    private fun iniciarGuia() {
        guiaActiva = true
        // Inflamos el layout de la guía sobre el root de la activity
        val guiaView = layoutInflater.inflate(R.layout.guia, null)
        val rootLayout = findViewById<ViewGroup>(android.R.id.content)
        rootLayout.addView(guiaView)

        // Bloqueo de interacción
        guiaView.isClickable = true
        guiaView.isFocusable = true
        guiaView.setOnTouchListener { _, _ -> true }

        val tvInfo = guiaView.findViewById<TextView>(R.id.tvMensaje)
        val btnNext = guiaView.findViewById<Button>(R.id.btnSiguiente)
        val btnSkip = guiaView.findViewById<Button>(R.id.btnOmitir)
        val card = guiaView.findViewById<CardView>(R.id.cardGuia)

        // Aplicación de animación de entrada para mejorar la UX
        val anim = AnimationUtils.loadAnimation(this, R.anim.pop_in)
        card.startAnimation(anim)

        btnNext.setOnClickListener {
            // Feedback auditivo al interactuar con la guía
            MediaPlayer.create(this, R.raw.clic).start()

            pasoActual++
            actualizarPaso(tvInfo, rootLayout, guiaView)
        }

        btnSkip.setOnClickListener {
            finalizarGuia(rootLayout, guiaView)
        }
    }
    //-------------------------------------------------------------------------------------------<<<

    //------------------------------------------------------------------------------------------->>>
    /**
     * Actualiza el contenido visual de la guía según el paso actual del tutorial.
     * Gestiona el texto informativo y el comportamiento de la capa de interacción.
     */
    private fun actualizarPaso(tv: TextView, root: ViewGroup, view: View) {
        // Cada vez que cambia el texto, lanzamos la animación de nuevo
        val anim = AnimationUtils.loadAnimation(this, R.anim.pop_in)
        tv.startAnimation(anim)
        when (pasoActual) {
            2 -> tv.text = "En esta pestaña conocerás a Spyro y sus amigos."
            3 -> tv.text = "Aquí podrás explorar los mundos mágicos."
            4 -> tv.text = "No olvides revisar tus coleccionables."
            5 -> tv.text = "Pulsa el icono 'i' arriba para ver los créditos."
            6 -> {
                tv.text = "¡Eso es todo! Disfruta la aventura."
                view.findViewById<Button>(R.id.btnSiguiente).text = "¡Vamos!"
            }
            else -> finalizarGuia(root, view)
        }
    }
    //-------------------------------------------------------------------------------------------<<<
    //------------------------------------------------------------------------------------------->>>
    /**
     * Función que finaliza la guía. Eliminando su vista y guardando el estado
     */
    private fun finalizarGuia(root: ViewGroup, view: View) {
        // Bloqueo de interacción
        guiaActiva = false

        root.removeView(view)
        getSharedPreferences("SpyroPrefs", Context.MODE_PRIVATE)
            .edit { putBoolean("firstRun", false) }
    }
    //-------------------------------------------------------------------------------------------<<<
    //------------------------------------------------------------------------------------------->>>
    /**
     * Método showSpyroBalloon
     * para definir el estilo púrpura y la animación
     */
    private fun showSpyroBalloon(anchor: View, text: String) {
        val balloon = Balloon.Builder(this)
            .setArrowSize(15)
            .setArrowPosition(0.5f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setWidthRatio(0.85f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText(text)
            .setTextColorResource(android.R.color.white)
            .setTextSize(17f)
            .setPadding(24)
            .setCornerRadius(30f)

            .setBackgroundColorResource(R.color.purple)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .setLifecycleOwner(this)
            .build()

        if (anchor.id == R.id.action_info) {
            balloon.showAlignBottom(anchor, 0, 10)
        } else {
            balloon.showAlignTop(anchor)
        }
    }
    //-------------------------------------------------------------------------------------------<<<

    private fun selectedBottomMenu(menuItem: MenuItem): Boolean {
        // Obtenemos la vista del icono pulsado para anclar el bocadillo
        val itemView = binding.navView.findViewById<View>(menuItem.itemId)

        when (menuItem.itemId) {
            R.id.nav_characters -> {
                navController?.navigate(R.id.navigation_characters)
                showSpyroBalloon(itemView, "Aquí podrás explorar a todos los personajes del mundo de Spyro.")
            }
            R.id.nav_worlds -> {
                navController?.navigate(R.id.navigation_worlds)
                showSpyroBalloon(itemView, "Aquí podrás explorar los mundos disponibles.")
            }
            R.id.nav_collectibles -> {
                navController?.navigate(R.id.navigation_collectibles)
                showSpyroBalloon(itemView, "Aquí podrás explorar los coleccionables.")
            }
        }
        return true
    }
    //------------------------------------------------------------------------------------------->>>

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Bloqueo de interacción
        if (guiaActiva) return true

        return if (item.itemId == R.id.action_info) {
            val infoView = findViewById<View>(R.id.action_info)

            val balloon = Balloon.Builder(this)
                .setArrowSize(15)
                .setArrowPosition(0.5f)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                .setWidthRatio(0.85f)
                .setHeight(BalloonSizeSpec.WRAP)
                .setText("Información detallada y créditos de la app.")
                .setTextSize(17f)
                .setPadding(10)
                .setCornerRadius(30f)
                .setBackgroundColorResource(R.color.purple)
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .setLifecycleOwner(this)
                // Cuando el bocadillo se cierre, abrimos el diálogo
                .setOnBalloonDismissListener {
                    showInfoDialog()
                }
                .build()

            balloon.showAlignBottom(infoView)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
    //-------------------------------------------------------------------------------------------<<<

    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_about)
            .setMessage(R.string.text_about)
            .setPositiveButton(R.string.accept, null)
            .show()
    }
}
