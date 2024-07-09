package com.canodevs.charlapp.Adaptador

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canodevs.charlapp.Chat.MensajesActivity
import com.canodevs.charlapp.Modelo.Usuario
import com.canodevs.charlapp.R

class AdapadorUsuario (context: Context, listaUsuarios: List<Usuario>, chatLeido: Boolean) : RecyclerView.Adapter<AdapadorUsuario.ViewHolder?>() {

    private val context: Context
    private val listaUsuarios : List<Usuario>
    private var chatLeido : Boolean

    init {
        this.context = context
        this.listaUsuarios = listaUsuarios
        this.chatLeido = chatLeido
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

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MensajesActivity::class.java)
            // Enviamos el uid del usuario seleccionado
            intent.putExtra("uid_usuario", usuario.getUid())
            Toast.makeText(context, "Usuario seleccionado: " + usuario.getN_Usuario(), Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
        }

    }
}