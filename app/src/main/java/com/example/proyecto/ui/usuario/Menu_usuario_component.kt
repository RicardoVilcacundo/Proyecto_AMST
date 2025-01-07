package com.example.proyecto.ui.usuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Menu_usuario_component : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is usuario Fragment"
    }
    val text: LiveData<String> = _text
}