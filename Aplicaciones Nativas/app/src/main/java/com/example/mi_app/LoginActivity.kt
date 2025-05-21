package com.example.mi_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity :  AppCompatActivity(){

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoRegistro: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnGoRegistro = findViewById(R.id.btnGoRegistro)

        btnLogin.setOnClickListener {
            login()
        }

        btnGoRegistro.setOnClickListener {
            // Ir a registro
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa email y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        val prefs = getSharedPreferences("UsuariosApp", Context.MODE_PRIVATE)
        val userData = prefs.getString(email, null)

        if(userData == null) {
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
            return
        }

        // userData formato: nombre|email|telefono|password
        val savedPassword = userData.split("|")[3]
        if(password == savedPassword) {
            Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
            // Guardar sesión simple
            val sessionPrefs = getSharedPreferences("SesionApp", Context.MODE_PRIVATE)
            sessionPrefs.edit().putString("usuarioActivo", email).apply()

            // Aquí iría al siguiente Activity (por ejemplo listado productos)
            val intent = Intent(this, ListadoProductosActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
        }
    }

}