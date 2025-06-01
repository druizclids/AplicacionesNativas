package com.example.mi_app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class ListadoProductosActivity : AppCompatActivity() {

    data class Producto(
        val id: Int,
        val nombre: String,
        val descripcion: String,
        val precio: Double,
        val imagenResId: Int,
        val imagenBitmap: Bitmap? = null
    )


    private lateinit var rvProductos: RecyclerView
    private lateinit var btnVerCarrito: Button
    private lateinit var btnCrearProducto: Button

    // Simulamos productos fijos
    private val productos = mutableListOf<Producto>(
        Producto(1, "Camiseta", "Camiseta de algodón", 30.0, R.drawable.camiseta),
        Producto(2, "Pantalón", "Pantalón jean azul", 50.0, R.drawable.pantalon),
        Producto(3, "Zapatos", "Zapatos deportivos", 80.0, R.drawable.zapatos)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_productos)

        rvProductos = findViewById(R.id.rvProductos)
        btnVerCarrito = findViewById(R.id.btnVerCarrito)
        btnCrearProducto = findViewById(R.id.btnCrearProducto)


        rvProductos.layoutManager = LinearLayoutManager(this)
        rvProductos.adapter = ProductoAdapter(productos) { producto ->
            agregarAlCarrito(producto)
        }

        btnVerCarrito.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }

        btnCrearProducto.setOnClickListener {
            val intent = Intent(this, CrearProductoActivity::class.java)
            startActivityForResult(intent, 1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val nombre = data.getStringExtra("nombre") ?: return
            val descripcion = data.getStringExtra("descripcion") ?: return
            val precio = data.getDoubleExtra("precio", 0.0)


            val nuevo = Producto(
                id = productos.size + 1,
                nombre = nombre,
                descripcion = descripcion,
                precio = precio,
                imagenResId = R.drawable.shampoo // imagen temporal
            )

            productos.add(nuevo)
            rvProductos.adapter?.notifyItemInserted(productos.size - 1)
        }
    }





    private fun agregarAlCarrito(producto: Producto) {
        // Guardamos en SharedPreferences el carrito simple como JSON (más adelante lo vemos)
        val prefs = getSharedPreferences("CarritoApp", MODE_PRIVATE)
        val carritoJson = prefs.getString("carrito", "[]")
        val gson = Gson()
        val carritoActual = gson.fromJson(carritoJson, Array<Producto>::class.java).toMutableList()
        carritoActual.add(producto)

        prefs.edit().putString("carrito", gson.toJson(carritoActual)).apply()
        Toast.makeText(this, "${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
    }
}
