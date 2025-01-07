package com.example.proyecto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _progress = MutableLiveData<Int>().apply {
        value = 0
    }
    val progress: LiveData<Int> = _progress

    private var timer: Timer? = null

    // Función para iniciar el temporizador
    fun startTimer() {
        timer = Timer()
        timer?.scheduleAtFixedRate(0, 1000) {  // Cada segundo
            if (_progress.value!! < 100) {
                _progress.postValue(_progress.value!! + 1)
            } else {
                timer?.cancel()  // Detener el temporizador cuando llega al 100%
            }
        }
    }

    // Detener el temporizador
    fun stopTimer() {
        timer?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()  // Cancelar el temporizador cuando el ViewModel se destruya
    }
}
