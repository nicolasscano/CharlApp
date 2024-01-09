package com.canodevs.charlapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Inicio : AppCompatActivity() {

    // Instanciamos 2 botones, uno para registro y otro para logeo
    private lateinit var Btn_ir_registros : Button
    private lateinit var Btn_ir_logeo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Asociamos cada botón por ID a su elemento del layout
        Btn_ir_registros = findViewById(R.id.Btn_ir_registro)
        Btn_ir_logeo = findViewById(R.id.Btn_ir_logeo)

        // Establecemos un Listener para que nos lleve a la pantalla de registro
        Btn_ir_registros.setOnClickListener() {
            val intent = Intent(this@Inicio, RegistroActivity::class.java)
            Toast.makeText(applicationContext, "Pantalla de registro", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

        // Establecemos un Listener para que nos lleve a la pantalla de login
        Btn_ir_logeo.setOnClickListener() {
            val intent = Intent(this@Inicio, LoginActivity::class.java)
            Toast.makeText(applicationContext, "Inicio de sesión", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
    }
}