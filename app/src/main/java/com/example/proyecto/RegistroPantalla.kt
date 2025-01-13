package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegistroPantalla : AppCompatActivity() {

    // Declarar FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_pantalla)

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Referencias a los elementos de la UI
        val emailEditText = findViewById<EditText>(R.id.registerEmailEditText)
        val passwordEditText = findViewById<EditText>(R.id.registerPasswordEditText)
        val registerButton = findViewById<Button>(R.id.registerUserButton)

        // Manejar el clic en el botón de registro
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 6) {
                    // Registrar al usuario con Firebase Authentication
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Registro exitoso
                                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                // Redirigir o realizar alguna acción adicional
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Error en el registro
                                val errorMessage = task.exception?.message
                                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
