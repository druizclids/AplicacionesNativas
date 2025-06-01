package com.example.mi_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


    class CarritoAdapter(
        private val productos: MutableList<ListadoProductosActivity.Producto>,
        private val onEliminarClick: (ListadoProductosActivity.Producto) -> Unit
    ) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

        inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
            val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
            val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
            return CarritoViewHolder(view)
        }

        override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
            val producto = productos[position]
            holder.tvNombre.text = producto.nombre
            holder.tvPrecio.text = "$${producto.precio}"
            holder.btnEliminar.setOnClickListener {
                onEliminarClick(producto)
            }
        }

        override fun getItemCount() = productos.size
    }
