package com.example.mi_app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CrearProductoActivity : AppCompatActivity(){
    private lateinit var etNombre: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etPrecio: EditText
    private lateinit var ivImagenProducto: ImageView
    private lateinit var btnTomarFoto: Button
    private lateinit var btnGuardar: Button

    private var imagenBitmap: Bitmap? = null

    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)

        etNombre = findViewById(R.id.etNombre)
        etDescripcion = findViewById(R.id.etDescripcion)
        etPrecio = findViewById(R.id.etPrecio)
        ivImagenProducto = findViewById(R.id.ivImagenProducto)
        btnTomarFoto = findViewById(R.id.btnTomarFoto)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnTomarFoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val descripcion = etDescripcion.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull()
            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precio != null && imagenBitmap != null) {
                val intent = Intent().apply {
                    putExtra("nombre", nombre)
                    putExtra("descripcion", descripcion)
                    putExtra("precio", precio)
                    putExtra("imagen", imagenBitmap)
                }


                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Completa todos los campos y toma una foto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imagenBitmap = data?.extras?.get("data") as Bitmap
            ivImagenProducto.setImageBitmap(imagenBitmap)
        }
    }


}