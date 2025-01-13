package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginPantalla : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_pantalla)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Obtener referencias a los elementos del layout
        val emailEditText = findViewById<EditText>(R.id.registerEmailEditText)
        val passwordEditText = findViewById<EditText>(R.id.registerPasswordEditText)
        val loginButton = findViewById<Button>(R.id.LoginUserButton)
        val registerButton = findViewById<TextView>(R.id.registrarButton)

        // Configurar el botón de login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Por favor ingrese un correo y una contraseña", Toast.LENGTH_SHORT).show()
            }
        }
        registerButton.setOnClickListener {
            val intent = Intent(this, RegistroPantalla::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Si el inicio de sesión es exitoso, redirigir al usuario
                    val user = auth.currentUser
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                    // Redirigir a la MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Finaliza la actividad actual
                } else {
                    // Si el inicio de sesión falla, mostrar un mensaje de error
                    Toast.makeText(this, "Error de autenticación: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
