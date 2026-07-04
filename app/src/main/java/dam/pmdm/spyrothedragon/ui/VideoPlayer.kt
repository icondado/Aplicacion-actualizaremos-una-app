package dam.pmdm.spyrothedragon.ui

import android.os.Bundle
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dam.pmdm.spyrothedragon.R

class VideoPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.video_player)

        val videoView = findViewById<VideoView>(R.id.videoView)
        // Definición de la ruta del recurso multimedia en la carpeta raw
        val path = "android.resource://" + packageName + "/" + R.raw.spyro_video
        videoView.setVideoPath(path)

        // Inicio automático de la reproducción una vez que el buffer está preparado
        videoView.setOnPreparedListener {
            videoView.start()
        }

        // Cierre de la actividad y liberación de recursos al finalizar el vídeo
        videoView.setOnCompletionListener {
            finish()
        }
    }
}