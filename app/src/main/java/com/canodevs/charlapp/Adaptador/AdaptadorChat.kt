package com.canodevs.charlapp.Adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canodevs.charlapp.Modelo.Chat
import com.canodevs.charlapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AdaptadorChat(contexto: Context, chatLista: List<Chat>, imagenUrl: String) :
    RecyclerView.Adapter<AdaptadorChat.ViewHolder?>() {

    private val contexto: Context
    private val chatLista: List<Chat>
    private val imagenUrl: String
    val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    init {
        this.contexto = contexto
        this.chatLista = chatLista
        this.imagenUrl = imagenUrl
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Vistas de item mensaje izquierdo
        var imagen_perfil_mensaje: ImageView? = null
        var TXT_ver_mensaje: TextView? = null
        var imagen_enviada_izquierdo: ImageView? = null
        var TXT_mensaje_visto: TextView? = null

        // Vistas de item mensaje derecho
        var imagen_enviada_derecha: ImageView? = null

        init {
            imagen_perfil_mensaje = itemView.findViewById(R.id.imagen_perfil_mensaje)
            TXT_ver_mensaje = itemView.findViewById(R.id.TXT_ver_mensaje)
            imagen_enviada_izquierdo = itemView.findViewById(R.id.imagen_enviada_izquierdo)
            TXT_mensaje_visto = itemView.findViewById(R.id.TXT_mensaje_visto)
            imagen_enviada_derecha = itemView.findViewById(R.id.imagen_enviada_derecha)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return if (position == 1) {
            val view: View = LayoutInflater.from(contexto)
                .inflate(com.canodevs.charlapp.R.layout.item_mensaje_derecho, parent, false)
            ViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(contexto)
                .inflate(com.canodevs.charlapp.R.layout.item_mensaje_izquierdo, parent, false)
            ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return chatLista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat: Chat = chatLista[position]
        Glide.with(contexto).load(imagenUrl).placeholder(R.drawable.ic_imagen_chat)
            .into(holder.imagen_perfil_mensaje!!)

        // Si el mensaje contiene imagen
        if (chat.getMensaje().equals("Imagen enviada") && !chat.getUrl().equals("")) {
            // Condición para el usuario que envía imagen como mensaje
            if (chat.getEmisor().equals(firebaseUser!!.uid)) {
                holder.TXT_ver_mensaje!!.visibility = View.GONE
                holder.imagen_enviada_derecha!!.visibility = View.VISIBLE
                Glide.with(contexto).load(chat.getUrl()).placeholder(R.drawable.ic_imagen_enviada).into(holder.imagen_enviada_derecha!!)
            }
            // Condición para el usuario que nos envía una imagen como mensaje
            else if (!chat.getEmisor().equals(firebaseUser!!.uid)) {
                holder.TXT_ver_mensaje!!.visibility = View.GONE
                holder.imagen_enviada_izquierdo!!.visibility = View.VISIBLE
                Glide.with(contexto).load(chat.getUrl()).placeholder(R.drawable.ic_imagen_enviada).into(holder.imagen_enviada_izquierdo!!)

            }
        }
        // Si el mensaje contiene sólo texto
        else {
            holder.TXT_ver_mensaje!!.text = chat.getMensaje()
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (chatLista[position].getEmisor().equals(firebaseUser!!.uid)) {
            1
        } else {
            0
        }
    }

}