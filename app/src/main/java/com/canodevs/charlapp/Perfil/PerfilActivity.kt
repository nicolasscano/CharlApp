package com.canodevs.charlapp.Perfil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.canodevs.charlapp.Modelo.Usuario
import com.canodevs.charlapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PerfilActivity : AppCompatActivity() {

    private lateinit var P_Imagen: ImageView
    private lateinit var P_n_usuario: TextView
    private lateinit var P_email: TextView
    private lateinit var P_nombres: EditText
    private lateinit var P_apellidos: EditText
    private lateinit var P_profesion: EditText
    private lateinit var P_domicilio: EditText
    private lateinit var P_edad: EditText
    private lateinit var P_telefono: EditText
    private lateinit var Btn_guardar: Button

    var user: FirebaseUser? = null
    var reference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        inicializarVariables()
        obtenerDatos()
    }

    private fun inicializarVariables() {
        P_Imagen = findViewById(R.id.P_imagen)
        P_n_usuario = findViewById(R.id.P_n_usuario)
        P_email = findViewById(R.id.P_email)
        P_nombres = findViewById(R.id.P_nombres)
        P_apellidos = findViewById(R.id.P_apellidos)
        P_profesion = findViewById(R.id.P_profesion)
        P_domicilio = findViewById(R.id.P_domicilio)
        P_edad = findViewById(R.id.P_edad)
        P_telefono = findViewById(R.id.P_telefono)
        Btn_guardar = findViewById(R.id.Btn_Guardar)

        user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(user!!.uid)

    }

    private fun obtenerDatos() {
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Obtenemos los datos de Firebase
                    val usuario : Usuario ?= snapshot.getValue(Usuario::class.java)
                    val str_n_usuario = usuario!!.getN_Usuario()
                    val str_email = usuario.getEmail()
                    val str_nombres = usuario.getNombres()
                    val str_apellidos = usuario.getApellidos()
                    val str_profesion = usuario.getProfesion()
                    val str_domicilio = usuario.getDomicilio()
                    val str_edad = usuario.getEdad()
                    val str_telefono = usuario.getTelefono()

                    // Seteamos la info en las vistas XML
                    P_n_usuario.text = str_n_usuario
                    P_email.text = str_email
                    P_nombres.setText(str_nombres)
                    P_apellidos.setText(str_apellidos)
                    P_profesion.setText(str_profesion)
                    P_domicilio.setText(str_domicilio)
                    P_edad.setText(str_edad)
                    P_telefono.setText(str_telefono)

                    Glide.with(applicationContext).load(usuario.getImagen()).placeholder(R.drawable.ic_item_usuario).into(P_Imagen)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }







}