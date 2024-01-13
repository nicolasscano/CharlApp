package com.canodevs.charlapp.Adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canodevs.charlapp.Modelo.Usuario
import com.canodevs.charlapp.R

class AdapadorUsuario (context: Context, listaUsuarios: List<Usuario>) : RecyclerView.Adapter<AdapadorUsuario.ViewHolder?>() {

    private val context: Context
    private val listaUsuarios : List<Usuario>

    init {
        this.context = context
        this.listaUsuarios = listaUsuarios
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nombre_usuario: TextView
        var email_usuario: TextView
        var imagen_usuario: ImageView

        init {
            nombre_usuario = itemView.findViewById(R.id.Item_nombre_usuario)
            email_usuario = itemView.findViewById(R.id.Item_email_usuario)
            imagen_usuario = itemView.findViewById(R.id.Item_imagen)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario : Usuario = listaUsuarios[position]
        holder.nombre_usuario.text = usuario.getN_Usuario()
        holder.email_usuario.text = usuario.getEmail()
        Glide.with(context).load(usuario.getImagen()).placeholder(R.drawable.ic_item_usuario).into(holder.imagen_usuario)
    }
}