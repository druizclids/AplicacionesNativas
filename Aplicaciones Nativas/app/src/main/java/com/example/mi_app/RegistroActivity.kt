package com.example.mi_app

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity(){
    private lateinit var etNombre: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        etNombre = findViewById(R.id.etNombre)
        etEmail = findViewById(R.id.etEmail)
        etTelefono = findViewById(R.id.etTelefono)
        etPassword = findViewById(R.id.etPassword)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            registrarCliente()
        }
    }

    private fun registrarCliente() {
        val nombre = etNombre.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar usuario en SharedPreferences (usaremos email como clave única)
        val prefs = getSharedPreferences("UsuariosApp", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        // Guardar datos concatenados en un string (para ejemplo simple)
        val userData = "$nombre|$email|$telefono|$password"
        editor.putString(email, userData)
        editor.apply()

        Toast.makeText(this, "Registro exitoso, ya puedes iniciar sesión", Toast.LENGTH_LONG).show()
        finish() // cerrar registro y volver a login
    }
}