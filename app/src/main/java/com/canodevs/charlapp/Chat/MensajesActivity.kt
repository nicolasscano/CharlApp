package com.canodevs.charlapp.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.canodevs.charlapp.Modelo.Usuario
import com.canodevs.charlapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MensajesActivity : AppCompatActivity() {

    private lateinit var imagen_perfil_chat: ImageView
    private lateinit var N_usuario_chat: TextView
    private lateinit var Et_mensaje: EditText
    private lateinit var IB_Enviar: ImageButton
    var uid_usuario_seleccionado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensajes)
        inicializarVistas()
        obtenerUid()
        leerInfoUsuarioSeleccionado()

        IB_Enviar.setOnClickListener {
            val mensaje = Et_mensaje.text.toString()
            if (mensaje.isEmpty()) {
                Toast.makeText(applicationContext, "Escribe un mensaje", Toast.LENGTH_SHORT).show()
            } else {
                enviarMensaje()
            }
        }
    }

    private fun obtenerUid() {
        intent = intent
        uid_usuario_seleccionado = intent.getStringExtra("uid_usuario").toString()
    }

    private fun enviarMensaje() {

    }

    private fun inicializarVistas() {
        imagen_perfil_chat = findViewById(R.id.imagen_perfil_chat)
        N_usuario_chat = findViewById(R.id.N_usuario_chat)
        Et_mensaje = findViewById(R.id.Et_mensaje)
        IB_Enviar = findViewById(R.id.IB_Enviar)
    }

    private fun leerInfoUsuarioSeleccionado() {
        val reference = FirebaseDatabase.getInstance().reference.child("Usuarios")
            .child(uid_usuario_seleccionado)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario: Usuario? = snapshot.getValue(Usuario::class.java)
                // Obtener nombre de usuario
                N_usuario_chat.text = usuario!!.getN_Usuario()
                // Obtener imagen de perfil
                Glide.with(applicationContext).load(usuario.getImagen())
                    .placeholder(R.drawable.ic_item_usuario)
                    .into(imagen_perfil_chat)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}