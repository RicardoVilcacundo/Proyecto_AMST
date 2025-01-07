package com.example.proyecto.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyecto.databinding.FragmentNotificationsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.Calendar

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Llamar a la función para graficar
        graficar()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun graficar() {
        // Obtener los tiempos de Pomodoro desde SharedPreferences
        val tiempos = List(7) { dia ->
            obtenerTiempo(dia) // Recuperar tiempo de cada día (lunes=0, martes=1, ...)
        }

        val barEntries = tiempos.mapIndexed { index, tiempo ->
            BarEntry(index.toFloat(), tiempo)
        }

        val diasSemana = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

        val barDataSet = BarDataSet(barEntries, "Uso de Pomodoro (minutos)")
        barDataSet.color = resources.getColor(com.google.android.material.R.color.design_default_color_primary, null)

        val barData = BarData(barDataSet)
        binding.graficaBarrass.data = barData

        val xAxis = binding.graficaBarrass.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(diasSemana)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        val yAxis = binding.graficaBarrass.axisLeft
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 240f // Hasta 240 minutos

        binding.graficaBarrass.axisRight.isEnabled = false
        binding.graficaBarrass.description.isEnabled = false
        binding.graficaBarrass.animateY(1000)
        binding.graficaBarrass.invalidate()
    }

    private fun obtenerTiempo(dia: Int): Float {
        val sharedPreferences = requireActivity().getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("tiempo_dia_$dia", 0f) // Recuperar tiempo (valor por defecto 0f)
    }

    // Función para obtener el día de la semana actual
    private fun obtenerDiaDeLaSemana(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_WEEK) - 1 // Devolver el índice del día de la semana (Lun = 0, Mar = 1, ...)
    }

    // Método para guardar el tiempo para el día actual
    private fun guardarTiempo(dia: Int, tiempo: Float) {
        val sharedPreferences = requireActivity().getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("tiempo_dia_$dia", tiempo)
        editor.apply()
    }
}


