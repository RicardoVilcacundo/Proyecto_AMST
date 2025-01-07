package com.example.proyecto.ui.usuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto.R
import com.example.proyecto.databinding.FragmentHomeBinding
import com.example.proyecto.databinding.FragmentMenuUsuarioBinding
import com.example.proyecto.databinding.FragmentNotificationsBinding
import com.example.proyecto.ui.notifications.NotificationsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [Menu_usuario.newInstance] factory method to
 * create an instance of this fragment.
 */
class Menu_usuario : Fragment() {

    private var _binding: FragmentMenuUsuarioBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val usuarioViewModel =
            ViewModelProvider(this).get(Menu_usuario_component::class.java)

        _binding = FragmentMenuUsuarioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}