package com.example.proyecto.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.proyecto.databinding.FragmentHomeBinding
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: ProgressBar
    private lateinit var startButton: Button
    private lateinit var resumeButton: Button
    private lateinit var finishButton: Button
    private lateinit var timeTextView: TextView

    private var isRunning = false
    private var isPaused = false
    private var progress = 0
    private val handler = Handler()

    private val totalDuration = 120000L // 2 minutos para pruebas
    private var timeLeft = totalDuration

    private val updateProgressRunnable = object : Runnable {
        override fun run() {
            if (isRunning && timeLeft > 0) {
                progress = ((totalDuration - timeLeft) * 100 / totalDuration).toInt()
                progressBar.progress = progress

                val minutes = (timeLeft / 1000) / 60
                val seconds = (timeLeft / 1000) % 60
                timeTextView.text = String.format("%02d:%02d", minutes, seconds)

                timeLeft -= 1000 // Decrementar 1 segundo
                handler.postDelayed(this, 1000)
            } else if (timeLeft <= 0) {
                finalizarTemporizador()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressBar = binding.idProgress
        startButton = binding.startButton
        resumeButton = binding.resumeButton
        finishButton = binding.finishButton
        timeTextView = binding.timeText

        configurarBotones()
        return root
    }

    private fun configurarBotones() {
        startButton.setOnClickListener {
            if (!isRunning && !isPaused) {
                iniciarTemporizador()
            } else if (isRunning) {
                pausarTemporizador()
            }
        }

        resumeButton.setOnClickListener {
            if (!isRunning && isPaused) {
                continuarTemporizador()
            }
        }

        finishButton.setOnClickListener {
            detenerTemporizador()
        }

        actualizarVisibilidadBotones()
    }

    private fun iniciarTemporizador() {
        isRunning = true
        isPaused = false
        handler.post(updateProgressRunnable)
        actualizarVisibilidadBotones()
    }

    private fun pausarTemporizador() {
        isRunning = false
        isPaused = true
        actualizarVisibilidadBotones()
    }

    private fun continuarTemporizador() {
        isRunning = true
        isPaused = false
        handler.post(updateProgressRunnable)
        actualizarVisibilidadBotones()
    }

    private fun detenerTemporizador() {
        isRunning = false
        isPaused = false
        timeLeft = totalDuration
        handler.removeCallbacks(updateProgressRunnable)
        progressBar.progress = 0
        timeTextView.text = "02:00" // Reiniciar a tiempo inicial
        actualizarVisibilidadBotones()
    }

    private fun finalizarTemporizador() {
        detenerTemporizador()

        // Obtener el día actual
        val diaActual = obtenerDiaDeLaSemana()

        // Recuperar el tiempo actual almacenado para el día
        val tiempoActual = obtenerTiempo(diaActual)

        // Sumar el tiempo de la sesión actual al tiempo acumulado
        val tiempoNuevo = tiempoActual + (totalDuration / 60000f) // Convertir de milisegundos a minutos

        // Guardar el nuevo tiempo acumulado en SharedPreferences
        guardarTiempo(diaActual, tiempoNuevo)
    }

    private fun actualizarVisibilidadBotones() {
        startButton.text = when {
            isRunning -> "Pausar"
            !isRunning && isPaused -> "Iniciar"
            else -> "Iniciar"
        }

        startButton.isGone = isPaused
        resumeButton.isGone = !isPaused
        finishButton.isGone = !isPaused
    }

    private fun guardarTiempo(dia: Int, tiempo: Float) {
        val sharedPreferences = requireActivity().getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Guardar el nuevo tiempo acumulado para el día específico
        editor.putFloat("tiempo_dia_$dia", tiempo)
        editor.apply()
    }

    private fun obtenerTiempo(dia: Int): Float {
        val sharedPreferences = requireActivity().getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("tiempo_dia_$dia", 0f) // Recuperar tiempo guardado para el día
    }

    // Función para obtener el día de la semana actual
    private fun obtenerDiaDeLaSemana(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_WEEK) - 3 // Devolver el índice del día de la semana (Lun = 0, Mar = 1, ... )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateProgressRunnable)
        _binding = null
    }
}
