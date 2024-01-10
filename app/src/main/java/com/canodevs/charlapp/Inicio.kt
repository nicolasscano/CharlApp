package com.canodevs.charlapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Inicio : AppCompatActivity() {

    // Instanciamos 2 botones, uno para registro y otro para logeo
    private lateinit var Btn_ir_registros: Button
    private lateinit var Btn_ir_logeo: Button

    var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Asociamos cada botón por ID a su elemento del layout
        Btn_ir_registros = findViewById(R.id.Btn_ir_registro)
        Btn_ir_logeo = findViewById(R.id.Btn_ir_logeo)

        // Establecemos un Listener para que nos lleve a la pantalla de registro
        Btn_ir_registros.setOnClickListener() {
            val intent = Intent(this@Inicio, RegistroActivity::class.java)
            Toast.makeText(applicationContext, "Pantalla de registro", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        // Establecemos un Listener para que nos lleve a la pantalla de login
        Btn_ir_logeo.setOnClickListener() {
            val intent = Intent(this@Inicio, LoginActivity::class.java)
            Toast.makeText(applicationContext, "Inicio de sesión", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }

    private fun comprobarSesion() {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val intent = Intent(this@Inicio, MainActivity::class.java)
            Toast.makeText(applicationContext, "Sesión de usuario activa", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()

        }
    }

    override fun onStart() {
        comprobarSesion()
        super.onStart()
    }
}