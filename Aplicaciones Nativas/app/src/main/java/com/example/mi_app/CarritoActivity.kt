package com.example.mi_app

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class CarritoActivity : AppCompatActivity(){
    private lateinit var rvCarrito: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnPagar: Button

    private val productosCarrito = mutableListOf<ListadoProductosActivity.Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        rvCarrito = findViewById(R.id.rvCarrito)
        tvTotal = findViewById(R.id.tvTotal)
        btnPagar = findViewById(R.id.btnPagar)

        rvCarrito.layoutManager = LinearLayoutManager(this)
        rvCarrito.adapter = CarritoAdapter(productosCarrito) { producto ->
            eliminarDelCarrito(producto)
        }

        cargarCarrito()
        actualizarTotal()

        btnPagar.setOnClickListener {
            Toast.makeText(this, "Compra finalizada. ¡Gracias!", Toast.LENGTH_LONG).show()
            vaciarCarrito()
            finish()
        }
    }

    private fun cargarCarrito() {
        val prefs = getSharedPreferences("CarritoApp", Context.MODE_PRIVATE)
        val carritoJson = prefs.getString("carrito", "[]")
        val gson = Gson()
        val productosArray = gson.fromJson(carritoJson, Array<ListadoProductosActivity.Producto>::class.java)
        productosCarrito.clear()
        productosCarrito.addAll(productosArray)
        rvCarrito.adapter?.notifyDataSetChanged()
    }

    private fun eliminarDelCarrito(producto: ListadoProductosActivity.Producto) {
        productosCarrito.remove(producto)
        rvCarrito.adapter?.notifyDataSetChanged()
        actualizarTotal()
        guardarCarrito()
    }

    private fun actualizarTotal() {
        val total = productosCarrito.sumOf { it.precio }
        tvTotal.text = "Total: $${"%.2f".format(total)}"
    }

    private fun guardarCarrito() {
        val prefs = getSharedPreferences("CarritoApp", Context.MODE_PRIVATE)
        val gson = Gson()
        prefs.edit().putString("carrito", gson.toJson(productosCarrito)).apply()
    }

    private fun vaciarCarrito() {
        productosCarrito.clear()
        guardarCarrito()
    }
}