package com.example.proyecto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyecto.R
import com.example.proyecto.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

   
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cambiar el título dinámicamente
        val username = "Juan"  // Aquí puedes obtener el nombre del usuario dinámicamente
        activity?.title = "Hola, $username"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configura el evento de clic del botón
        binding.buttonPruebaa.setOnClickListener {
            // Usamos NavController para navegar a EncuestaFragment
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_encuesta)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
